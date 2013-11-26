package cn.ccrise.spimp.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import cn.ccrise.ikjp.core.util.Inflector;
import cn.ccrise.spimp.util.PageFields;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 前端代码生成器，包括JS代码及JSP代码; 使用说明：在开发一个新模块时，
 * 1、配置关键路径(packageName,uriPrefix,entityName)； 2、首先使用generateEntityFile()生成实体类；
 * 3、打开生成的实体类，增加属性，并增加用于生成前端代码的注解； 4、使用generateOtherFiles()方法生成其余代码； 5、完成；
 * 
 * <p>
 * 
 * @author David(david.kosoon@gmail.com)
 */
public class PageGenerator {

	/**
	 * Java源码前缀.
	 */
	private static final String SOURCE_PREFIX = "src/main/java/";

	/**
	 * Web JS 源码前缀.
	 */
	private static final String JS_SOURCE_PREFIX = "src/main/webapp/WEB-INF/resources/scripts/app/";

	/**
	 * Web JSP 源码前缀.
	 */
	private static final String JSP_SOURCE_PREFIX = "src/main/webapp/WEB-INF/views/";

	/**
	 * 服务层包名.
	 */
	private static final String SERVICE = "service";
	/**
	 * 控制层包名.
	 */
	private static final String CONTROLLER = "web";

	/**
	 * DAO层包名.
	 */
	private static final String ACCESS = "access";
	/**
	 * 实体层包名.
	 */
	private static final String ENTITY = "entity";

	private static final String ENCODING = "UTF-8";

	protected static final String PAGE_TITLE = "防治水信息管理 - 安全生产综合管理平台";

	public static void generateController(final String entityName, final String packageName, final String uriPrefix) {
		String path = SOURCE_PREFIX + packageName.replace(".", "/") + "/" + CONTROLLER + "/";
		generateFiles(path, entityName, packageName, "controller.ftl", entityName + "Controller.java", uriPrefix);
	}

	public static void generateDAO(final String entityName, final String packageName, final String uriPrefix) {
		String path = SOURCE_PREFIX + packageName.replace(".", "/") + "/" + ACCESS + "/";
		generateFiles(path, entityName, packageName, "dao.ftl", entityName + "DAO.java", uriPrefix);
	}

	public static void generateEntity(final String entityName, final String packageName, final String uriPrefix) {
		String path = SOURCE_PREFIX + packageName.replace(".", "/") + "/" + ENTITY + "/";
		generateFiles(path, entityName, packageName, "entity.ftl", entityName + ".java", uriPrefix);
	}

	public static void generateService(final String entityName, final String packageName, final String suffix) {
		String path = SOURCE_PREFIX + packageName.replace(".", "/") + "/" + SERVICE + "/";
		generateFiles(path, entityName, packageName, "service.ftl", entityName + "Service.java", suffix);
	}

	/**
	 * 生成控制器.
	 * 
	 * @param entityName
	 *            实体名称
	 * @param packageName
	 *            包名
	 * @param uriPrefix
	 *            uri前缀
	 */
	private static void generateFiles(final String path, final String entityName, final String packageName,
			final String ftl, final String suffix, final String uriPrefix) {
		try {
			Template template = getConfiguration().getTemplate(ftl, ENCODING);
			FileUtils.forceMkdir(new File(path));
			Writer writer = new FileWriter(path + suffix);
			template.process(prepareDataMap(entityName, packageName, uriPrefix), writer);
			writer.flush();
		} catch (IOException e) {
			System.err.println("读取不到模板文件");
			e.printStackTrace();
		} catch (TemplateException e) {
			System.err.println("模板文件异常");
		}
	}

	private static String getColumnType(Class<?> fieldType) {
		if (String.class == fieldType) {
			return "TEXT";
		}
		if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
			return "NUMBER";
		}
		if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
			return "NUMBER";
		}
		if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
			return "NUMBER";
		}
		if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
			return "NUMBER";
		}
		if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
			return "NUMBER";
		}
		if (Date.class == fieldType) {
			return "DATE";
		}
		if (Timestamp.class == fieldType) {
			return "TIMESTAMP";
		}
		if (Blob.class == fieldType) {
			return "BLOB";
		}

		return "ENTITY";
	}

	/**
	 * 获取默认配置.
	 * 
	 * @return 模板配置
	 */
	private static Configuration getConfiguration() {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(PageGenerator.class, "");
		cfg.setCacheStorage(new NullCacheStorage());
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		return cfg;
	}

	/**
	 * 准备模板填充数据.
	 * 
	 * @param entityName
	 *            实体名称
	 * @param packageName
	 *            包名
	 * @return 数据Map
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Object> prepareDataMap(final String entityName, final String packageName,
			final String uriPrefix) {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 父级目录层级
		int parentDirNum = uriPrefix.split("/").length + 1;
		StringBuffer parentDir = new StringBuffer();
		for (int i = 0; i < parentDirNum; i++) {
			parentDir.append("../");
		}
		dataMap.put("parentDir", parentDir.toString());

		// 数据操作URI
		dataMap.put("path", uriPrefix + "/" + Inflector.getInstance().underscore(entityName).replace("_", "-"));
		dataMap.put("restPath", "/" + uriPrefix + "/" + Inflector.getInstance().tableize(entityName).replace("_", "-"));
		dataMap.put("jsPath", "${resources}/scripts/app/" + uriPrefix + "/" + entityName.toLowerCase() + "/index");

		StringBuffer jsFields = new StringBuffer();
		StringBuffer validateCode = new StringBuffer();
		StringBuffer formFields = new StringBuffer();
		StringBuffer detailFormFields = new StringBuffer();
		StringBuffer detailShowJs = new StringBuffer();
		StringBuffer queryPlaceHolder = new StringBuffer();

		StringBuffer headers = new StringBuffer();
		StringBuffer queryParams = new StringBuffer();
		StringBuffer queryParamsWithType = new StringBuffer();
		StringBuffer dbQuery = new StringBuffer();
		StringBuffer dateTimeInitJS = new StringBuffer();
		StringBuffer selectQuery = new StringBuffer();
		StringBuffer selectInitJS = new StringBuffer();
		StringBuffer selectChangeJS = new StringBuffer();
		StringBuffer selectQueryHtml = new StringBuffer();

		boolean dateQuery = false;
		boolean hasDateTime = false;
		boolean dateTimeQuery = false;
		boolean hasTextSearch = false;
		boolean hasSelect = false;
		boolean hasSelectSearch = false;
		boolean hasEntity = false;
		String dateQueryField = null;
		Class clazz = null;
		try {
			clazz = Class.forName(packageName + ".entity." + entityName);
		} catch (Exception e) {
			System.err.println("未找到实体类:" + packageName + ".entity." + entityName);
		}

		if (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!field.isAnnotationPresent(PageFields.class)) {
					continue;
				}
				PageFields column = field.getAnnotation(PageFields.class);
				StringBuffer selectFields = new StringBuffer();

				String columnDescription = column.describtion();
				String columnName = field.getName();
				String selectDataUri = column.selectDataUri();
				String selectShowField = column.selectShowField();
				int columnWidth = column.columnWidth();
				boolean columnShow = column.columnShow();
				boolean allowedNull = column.allowedNull();
				boolean query = column.search();
				String columnType = "";
				if (StringUtils.isBlank(column.type())) {
					columnType = getColumnType(field.getType());
				} else {
					columnType = column.type();
				}

				// 查询相关的Java代码及JS代码
				if (query) {
					if ("date".equalsIgnoreCase(columnType) && !dateQuery && !dateTimeQuery) {
						queryParamsWithType.append(", Date startDate, Date endDate");
						queryParams.append(", startDate, endDate");
						dateQuery = true;
						dateQueryField = columnName;
					} else if ("timestamp".equalsIgnoreCase(columnType) && !dateTimeQuery && !dateQuery) {
						queryParamsWithType.append(", String startDate, String endDate");
						queryParams.append(", startDate, endDate");
						dateTimeQuery = true;
						dateQueryField = columnName;
					} else if ("text".equalsIgnoreCase(columnType) || "textarea".equalsIgnoreCase(columnType)) {
						if (!hasTextSearch) {
							queryParamsWithType.append(",String search");
							queryParams.append(", search");
							hasTextSearch = true;
						}
						queryPlaceHolder.append(columnDescription).append("/");
						dbQuery.append(",Restrictions.ilike(\"").append(columnName)
								.append("\", search, MatchMode.ANYWHERE)");
					} else if ("select".equalsIgnoreCase(columnType)) {
						hasSelectSearch = true;
						queryParamsWithType.append(",Long ").append(columnName);
						queryParams.append(", ").append(columnName);

						selectFields.append(",'search_").append(columnName).append("'");

						selectQuery.append("\t\tif (").append(columnName).append(" !== null){\n")
								.append("\t\t\tcriterions.add(Restrictions.eq(\"").append(columnName).append(".id\", ")
								.append(columnName).append("));\n").append("\t\t}\n");

						selectChangeJS.append("\t$('#search_").append(columnName)
								.append("').bind('change',function(){\n")
								.append("\t\t$('#submit').trigger('click');\n").append("\t});\n");

						selectQueryHtml.append("\t\t\t\t\t\t<select id=\"search_").append(columnName)
								.append("\" name=\"").append(columnName)
								.append("\" style=\"height:25px;width:120px;font-size:12px;\"></select>\n");
					}
				}

				// 初始化select的JS代码
				if ("select".equalsIgnoreCase(columnType)) {
					hasSelect = true;
					hasEntity = true;
					selectFields.append(",'create_").append(columnName).append("'");
					selectFields.append(",'edit_").append(columnName).append("'");

					selectInitJS.append("\tUtils.select.remote([ ")
							.append(selectFields.toString().replaceFirst(",", "")).append(" ], '")
							.append(selectDataUri).append("', 'id', '").append(selectShowField).append("',true,'")
							.append(columnDescription).append("');\n");
				}

				// 初始化日期时间选择控件的JS代码
				if ("timestamp".equalsIgnoreCase(columnType)) {
					hasDateTime = true;

					dateTimeInitJS.append("\t$('#create_").append(columnName).append("').datetimepicker({\n")
							.append("\t\tformat: 'yyyy-mm-dd hh:ii:ss'\n").append("\t});\n\n");

					dateTimeInitJS.append("\t$('#edit_").append(columnName).append("').datetimepicker({\n")
							.append("\t\tformat: 'yyyy-mm-dd hh:ii:ss'\n").append("\t});\n");
				}

				// Grid的options的JS代码
				if (columnShow) {
					jsFields.append("\t\t{\n").append("\t\t\theader : '").append(columnDescription).append("',\n");

					if ("number".equalsIgnoreCase(columnType)) {
						jsFields.append("\t\t\talign : 'right',\n");
					}

					if (columnWidth == 0) {
						if ("date".equalsIgnoreCase(columnType)) {
							columnWidth = 90;
						} else if ("timestamp".equalsIgnoreCase(columnType)) {
							columnWidth = 145;
						} else if ("number".equalsIgnoreCase(columnType)) {
							columnWidth = 80;
						}

						if (columnDescription.indexOf("人") != -1 || columnDescription.indexOf("姓名") != -1) {
							columnWidth = 80;
						}
					}

					if (columnWidth != 0) {
						jsFields.append("\t\t\twidth : ").append(columnWidth).append(",\n");
					}

					jsFields.append("\t\t\tname : '").append(columnName).append("'\n");

					if ("select".equalsIgnoreCase(columnType)) {
						jsFields.append("\t\t\t,render : function(value) {\n")
								.append("\t\t\t\treturn value === null ? '' : value.").append(selectShowField)
								.append(";\n").append("\t\t\t}\n");
					}

					jsFields.append("\t\t},\n");
				}

				// 验证的JS代码
				if (!allowedNull) {
					validateCode.append("\t\tif (model.").append(columnName);

					if ("select".equalsIgnoreCase(columnType)) {
						validateCode.append(".id");
					}

					validateCode.append(" === '') {\n").append("\t\t\terrorMsg.push('请输入").append(columnDescription)
							.append("');\n").append("\t\t}\n\n");
				}

				if ("number".equalsIgnoreCase(columnType)) {
					validateCode.append("\t\tif (model.").append(columnName).append(" !== '' && !$.isNumeric(model.")
							.append(columnName).append(")) {\n").append("\t\t\terrorMsg.push('")
							.append(columnDescription).append("为数字格式');\n").append("\t\t}\n\n");
				}

				// 查看详情的JS代码
				if ("entity".equalsIgnoreCase(columnType)) {
					hasEntity = true;
				}

				if ("select".equalsIgnoreCase(columnType) || "entity".equalsIgnoreCase(columnType)) {
					detailShowJs.append("\t\tobject.").append(columnName).append(" = object.").append(columnName)
							.append(".").append(selectShowField).append(";\n");
				}

				// 新建和编辑表单元素
				formFields.append("\t\t\t\t\t\t<div class=\"control-group\">\n")
						.append("\t\t\t\t\t\t\t<label class=\"control-label\" for=\"").append(columnName).append("\">")
						.append(columnDescription).append("</label>\n")
						.append("\t\t\t\t\t\t\t<div class=\"controls\">\n");

				if ("text".equalsIgnoreCase(columnType) || "number".equalsIgnoreCase(columnType)) {
					formFields.append("\t\t\t\t\t\t\t\t<input id=\"xxxx_").append(columnName).append("\" name=\"")
							.append(columnName).append("\" type=\"").append(columnType.toLowerCase()).append("\">\n");
				} else if ("textarea".equalsIgnoreCase(columnType)) {
					formFields.append("\t\t\t\t\t\t\t\t<textarea id=\"xxxx_").append(columnName).append("\" name=\"")
							.append(columnName).append("\" rows=3></textarea>\n");
				} else if ("date".equalsIgnoreCase(columnType)) {
					formFields
							.append("\t\t\t\t\t\t\t\t<input type=\"datetime\" placeholder=\"请选择\" class=\"input-small\" autocomplete=\"off\" id=\"xxxx_")
							.append(columnName).append("\" name=\"").append(columnName).append("\">\n");
				} else if ("select".equalsIgnoreCase(columnType)) {
					formFields.append("\t\t\t\t\t\t\t\t<select id=\"xxxx_").append(columnName).append("\" name=\"")
							.append(columnName).append("[id]\"></select>\n");
				} else { // 提供未知类型的默认值
					formFields.append("\t\t\t\t\t\t\t\t<input id=\"xxxx_").append(columnName).append("\" name=\"")
							.append(columnName).append("\" type=\"text\">\n");
				}
				formFields.append("\t\t\t\t\t\t\t</div>\n").append("\t\t\t\t\t\t</div>\n");

				// 查看详情表单元素
				detailFormFields.append("\t\t\t\t\t\t<div class=\"control-group\">\n")
						.append("\t\t\t\t\t\t\t<label class=\"control-label\" for=\"").append(columnName).append("\">")
						.append(columnDescription).append("</label>\n")
						.append("\t\t\t\t\t\t\t<div class=\"controls\">\n");
				if (!"textarea".equalsIgnoreCase(columnType)) {
					detailFormFields.append("\t\t\t\t\t\t\t\t<input id=\"detail_").append(columnName)
							.append("\" name=\"").append(columnName)
							.append("\" type=\"text\" readonly=\"readonly\">\n");
				} else {
					detailFormFields.append("\t\t\t\t\t\t\t\t<textarea id=\"detail_").append(columnName)
							.append("\" name=\"").append(columnName)
							.append("\" rows=3 readonly=\"readonly\"></textarea>\n");
				}
				detailFormFields.append("\t\t\t\t\t\t\t</div>\n").append("\t\t\t\t\t\t</div>\n");

				headers.append(",\"").append(columnDescription).append("\"");
			}

			jsFields.append("\t\t{\n")
					.append("\t\t\theader : '查看',\n")
					.append("\t\t\tname : 'id',\n")
					.append("\t\t\twidth : 50,\n")
					.append("\t\t\talign : 'center',\n")
					.append("\t\t\trender : function(value) {\n")
					.append("\t\t\t\treturn '<i data-role=\"detail\" class=\"icon-list\" style=\"cursor:pointer;\"></i>';\n")
					.append("\t\t\t}\n").append("\t\t}");
		}

		dataMap.put("queryParam", queryParams.toString());
		dataMap.put("selectQueryHtml", selectQueryHtml.toString());
		dataMap.put("dateQuery", dateQuery);
		dataMap.put("dateTimeQuery", dateTimeQuery);
		dataMap.put("hasDateTime", hasDateTime);
		dataMap.put("dateTimeInitJS", dateTimeInitJS.toString());
		dataMap.put("selectQuery", selectQuery.toString());
		dataMap.put("hasTextSearch", hasTextSearch);
		dataMap.put("selectChangeJS", selectChangeJS.toString());
		dataMap.put("hasSelectSearch", hasSelectSearch);
		dataMap.put("selectInitJS", selectInitJS.toString());
		dataMap.put("hasSelect", hasSelect);
		dataMap.put("hasEntity", hasEntity);
		dataMap.put("dateQueryField", dateQueryField);
		dataMap.put("queryParamsWithType", queryParamsWithType.toString());
		dataMap.put("jsFields", jsFields.toString());
		dataMap.put("validateCode", validateCode.toString());
		dataMap.put("detailShowJs", detailShowJs.toString());
		dataMap.put("createFormFields", formFields.toString().replaceAll("xxxx", "create"));
		dataMap.put("editFormFields", formFields.toString().replaceAll("xxxx", "edit"));
		dataMap.put("detailFormFields", detailFormFields.toString());
		dataMap.put("pageTitle", PAGE_TITLE);
		if (queryPlaceHolder.toString().length() > 1) {
			dataMap.put(
					"queryPlaceHolder",
					new StringBuffer("输入")
							.append(queryPlaceHolder.toString().substring(0, queryPlaceHolder.toString().length() - 1))
							.append("...").toString());
		}

		String[] s = packageName.split("\\.");
		String databasePrefix = s[s.length - 1];
		dataMap.put("databasePrefix", databasePrefix);
		dataMap.put("tableName", Inflector.getInstance().tableize(entityName));
		dataMap.put("packageName", packageName);
		dataMap.put("entityName", entityName);

		dataMap.put("headers", headers.toString().replaceFirst(",", ""));
		dataMap.put("dbQuery", dbQuery.toString().replaceFirst(",", ""));

		return dataMap;
	}

	/**
	 * 第一步:配置源码路径。
	 */
	protected final String packageName = "cn.ccrise.spimp.spmi.water";

	protected final String uriPrefix = "spmi/water";

	protected final String entityName = "Water";

	@Test
	public void generateController() {
		generateController(entityName, packageName, uriPrefix);
	}

	@Test
	public void generateDao() {
		generateDAO(entityName, packageName, uriPrefix);
	}

	@Test
	public void generateEntity() {
		generateEntity(entityName, packageName, uriPrefix);
	}

	/**
	 * 第二步:生成后台DAO及ENTITY代码。
	 */
	@Test
	public void generateEntityFile() {
		generateEntity(entityName, packageName, uriPrefix);
	}

	@Test
	public void generateJs() {
		generateJs(entityName, packageName, uriPrefix);
	}

	/**
	 * 第三步:打开刚生成的实体类，添加属性及注解。
	 */

	@Test
	public void generateJsp() {
		generateJsp(entityName, packageName, uriPrefix);
	}

	/**
	 * 第四步:生成前端代码，包括js,jsp,controller,service,dao。
	 */
	@Test
	public void generateOtherFiles() {
		generateJs(entityName, packageName, uriPrefix);
		generateJsp(entityName, packageName, uriPrefix);

		generateDAO(entityName, packageName, uriPrefix);
		generateService(entityName, packageName, uriPrefix);
		generateController(entityName, packageName, uriPrefix);
	}

	@Test
	public void generateService() {
		generateService(entityName, packageName, uriPrefix);
	}

	private void generateJs(final String entityName, final String packageName, final String uriPrefix) {
		String path = JS_SOURCE_PREFIX + uriPrefix + "/" + entityName.toLowerCase() + "/";
		generateFiles(path, entityName, packageName, "js.ftl", "index.js", uriPrefix);
	}

	private void generateJsp(final String entityName, final String packageName, final String uriPrefix) {
		String path = JSP_SOURCE_PREFIX + uriPrefix + "/" + entityName.toLowerCase() + "/";
		generateFiles(path, entityName, packageName, "jsp.ftl", "index.jsp", uriPrefix);
	}
}
