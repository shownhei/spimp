package cn.ccrise.spimp.entity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Date;
import java.util.Map;

import org.junit.Test;

import cn.ccrise.ikjp.core.util.Inflector;
import cn.ccrise.spimp.util.PageFields;

import com.google.common.collect.Maps;

import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 前端代码生成器，包括JS代码及JSP代码; 使用说明：在开发一个新模块时， 1、首先使用CodeGenerator生成后端服务代码框架；
 * 2、打开生成的实体类，增加属性，并增加用于生成前端代码的注解； 3、使用PageGenerator的generateWebPages方法生成前端代码；
 * 4、完成；
 * 
 * <p>
 * 
 * @author David(david.kosoon@gmail.com)
 */
public class PageGenerator extends CodeGenerator {

	/**
	 * Web JS 源码前缀.
	 */
	private static final String JS_SOURCE_PREFIX = "src/main/webapp/WEB-INF/resources/scripts/app/";

	/**
	 * Web JSP 源码前缀.
	 */
	private static final String JSP_SOURCE_PREFIX = "src/main/webapp/WEB-INF/views/";

	private static final String ENCODING = "UTF-8";

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
	private static void generate(final String path, final String entityName, final String packageName,
			final String ftl, final String suffix, final String uriPrefix) {
		try {
			Template template = getConfiguration().getTemplate(ftl, ENCODING);
			Writer writer = new FileWriter(path + suffix);
			template.process(prepareDataMap(entityName, packageName, uriPrefix), writer);
			writer.flush();
		} catch (IOException e) {
			System.err.println("读取不到模板文件");
		} catch (TemplateException e) {
			System.err.println("模板文件异常");
		} catch (ClassNotFoundException e) {
			System.err.println("未找到实体类:" + packageName + "." + entityName);
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
		if (Blob.class == fieldType) {
			return "BLOB";
		}

		return "TEXT";
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
	private static Map<String, String> prepareDataMap(final String entityName, final String packageName,
			final String uriPrefix) throws ClassNotFoundException {
		Map<String, String> dataMap = Maps.newHashMap();

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
		StringBuffer queryPlaceHolder = new StringBuffer();

		Class clazz = Class.forName(packageName + ".entity." + entityName);
		Field[] fields = clazz.getDeclaredFields();
		int loop = 0;
		for (Field field : fields) {
			if (!field.isAnnotationPresent(PageFields.class)) {
				continue;
			}
			PageFields column = field.getAnnotation(PageFields.class);

			String columnDescription = column.describtion();
			String columnName = field.getName();
			boolean columnShow = column.columnShow();
			boolean allowedNull = column.allowedNull();
			boolean query = column.search();
			String columnType = "";
			if (column.type().equals("")) {
				columnType = getColumnType(field.getType());
			} else {
				columnType = column.type();
			}

			if (query) {
				queryPlaceHolder.append(columnDescription).append("/");
			}

			if (columnShow) {
				jsFields.append("\t\t{\n").append("\t\t\theader : '").append(columnDescription).append("',\n")
						.append("\t\t\tname : '").append(columnName).append("'\n");
				if (loop != fields.length - 1) {
					jsFields.append("\t\t},\n");
				} else {
					jsFields.append("\t\t}");
				}
			}

			if (!allowedNull) {
				validateCode.append("\t\tif (model.").append(columnName).append(" === '') {\n")
						.append("\t\t\terrorMsg.push('请输入").append(columnDescription).append("');\n")
						.append("\t\t}\n\n");
			}

			if ("NUMBER".equals(columnType)) {
				validateCode.append("\t\tif (!$.isNumeric(model.").append(columnName).append(")) {\n")
						.append("\t\t\terrorMsg.push('").append(columnDescription).append("为数字格式');\n")
						.append("\t\t}\n\n");
			}

			formFields.append("\t\t\t\t\t\t<div class=\"control-group\">\n")
					.append("\t\t\t\t\t\t\t<label class=\"control-label\" for=\"").append(columnName).append("\">")
					.append(columnDescription).append("</label>\n").append("\t\t\t\t\t\t\t<div class=\"controls\">\n");

			if ("text".equalsIgnoreCase(columnType)) {
				formFields.append("\t\t\t\t\t\t\t\t<input id=\"").append(columnName).append("\" name=\"")
						.append(columnName).append("\" type=\"text\">\n");
			} else if ("textarea".equalsIgnoreCase(columnType)) {
				formFields.append("\t\t\t\t\t\t\t\t<textarea id=\"").append(columnName).append("\" name=\"")
						.append(columnName).append("\" rows=3></textarea>\n");
			} else if ("date".equalsIgnoreCase(columnType)) {
				formFields
						.append("\t\t\t\t\t\t\t\t<input type=\"datetime\" placeholder=\"请选择\" class=\"input-small\" autocomplete=\"off\" id=\"")
						.append(columnName).append("\" name=\"").append(columnName).append("\">\n");
			}

			formFields.append("\t\t\t\t\t\t\t</div>\n").append("\t\t\t\t\t\t</div>\n");

			loop++;
		}

		dataMap.put("jsFields", jsFields.toString());
		dataMap.put("validateCode", validateCode.toString());
		dataMap.put("formFields", formFields.toString());
		dataMap.put("pageTitle", PAGE_TITLE);
		if (queryPlaceHolder.toString().length() > 1) {
			dataMap.put(
					"queryPlaceHolder",
					new StringBuffer("输入")
							.append(queryPlaceHolder.toString().substring(0, queryPlaceHolder.toString().length() - 1))
							.append("...").toString());
		}

		return dataMap;
	}

	@Test
	public void generateJs() {
		generateJs(entityName, packageName, uriPrefix);
	}

	@Test
	public void generateJsp() {
		generateJsp(entityName, packageName, uriPrefix);
	}

	/**
	 * 生成前端代码，默认情况下使用此方法。
	 */
	@Test
	public void generateWebPages() {
		generateJs(entityName, packageName, uriPrefix);
		generateJsp(entityName, packageName, uriPrefix);
	}

	private void generateJs(final String entityName, final String packageName, final String uriPrefix) {
		String path = JS_SOURCE_PREFIX + uriPrefix + "/" + entityName.toLowerCase() + "/";
		generate(path, entityName, packageName, "js.ftl", "index.js", uriPrefix);
	}

	private void generateJsp(final String entityName, final String packageName, final String uriPrefix) {
		String path = JSP_SOURCE_PREFIX + uriPrefix + "/" + entityName.toLowerCase() + "/";
		generate(path, entityName, packageName, "jsp.ftl", "index.jsp", uriPrefix);
	}
}
