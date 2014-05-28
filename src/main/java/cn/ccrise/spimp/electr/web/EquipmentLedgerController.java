/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.AnnualOil;
import cn.ccrise.spimp.electr.entity.EquipmentLedger;
import cn.ccrise.spimp.electr.service.EquipmentLedgerService;
import cn.ccrise.spimp.system.web.UploadController;
import cn.ccrise.spimp.util.ExcelCallBackInteface;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * EquipmentLedger Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class EquipmentLedgerController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EquipmentLedgerService equipmentLedgerService;

	@RequestMapping(value = "/ignore/equipment/equipment-ledgers/detail", method = RequestMethod.GET)
	public ModelAndView getPlan(Long equipmentId) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		if (equipmentId != null) {
			EquipmentLedger instance = equipmentLedgerService.get(equipmentId);
			root.put("equipment", instance);
		} else {
			root.put("equipment", new EquipmentLedger());
		}
		return new ModelAndView("electr/equipment/equipmentledger/detail", root);
	}

	@RequestMapping(value = "/electr/equipment/equipment-ledgers/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(equipmentLedgerService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/equipment-ledgers/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(equipmentLedgerService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/equipment-ledger", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/equipmentledger/index";
	}

	@RequestMapping(value = "/electr/equipment/equipment-ledgers", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<EquipmentLedger> page, Long deviceClass, String search) {
		page = equipmentLedgerService.pageQuery(page, deviceClass, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/equipment-ledgers", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody EquipmentLedger equipmentLedger) {
		return new Response(equipmentLedgerService.save(equipmentLedger));
	}

	@RequestMapping(value = "/electr/equipment/equipment-ledgers/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody EquipmentLedger equipmentLedger, @PathVariable long id) {
		return new Response(equipmentLedgerService.update(equipmentLedger));
	}

	@RequestMapping(value = "/electr/equipment/equipment-ledgers/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, Long deviceClass, String search)
			throws Exception {
		Page<EquipmentLedger> page = new Page<EquipmentLedger>();
		page.setPageSize(900000);
		page = equipmentLedgerService.pageQuery(page, deviceClass, search);
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("result", page.getResult());
		new ExcelHelper<AnnualOil>().genExcelWithTel(httpSession, response, "electr/equipment/equipment-ledgers.xls",
				root, "设备台账", new String[] { "所有设备" }, new ExcelCallBackInteface() {

					@SuppressWarnings("unused")
					@Override
					public void process(Workbook book, HashMap<String, Object> root) {

						final int startRow = 3;// 开始行
						final int startCol = 0;// 开始列
						final int colCount = 27;// 属性个数
						Sheet sheet = book.getSheetAt(0);
						@SuppressWarnings("unchecked")
						List<EquipmentLedger> result = (List<EquipmentLedger>) root.get("result");
						Row currentRow = null;
						int rowIndex = startRow;
						int colIndex = startCol;
						Cell currentCell = null;
						CellStyle style = sheet.getRow(3).getCell(0).getCellStyle();
						style.setAlignment(CellStyle.ALIGN_LEFT);
						for (EquipmentLedger equipment : result) {
							currentRow = sheet.createRow(rowIndex++);
							for (int col = 0; col < colCount; col++) {
								currentCell = currentRow.createCell(col);
								currentCell.setCellValue("");
								currentCell.setCellStyle(style);
							}
						}
						rowIndex = startRow;
						for (EquipmentLedger equipment : result) {
							Row row = sheet.getRow(rowIndex++);
							colIndex = startCol;
							// "设备分类",
							row.getCell(colIndex++).setCellValue(equipment.getDeviceClass().getItemName());
							// "设备名称",
							row.getCell(colIndex++).setCellValue(equipment.getDeviceName());
							// "设备编号",
							row.getCell(colIndex++).setCellValue(equipment.getEquipmentID());
							// "规格型号",
							row.getCell(colIndex++).setCellValue(equipment.getDeviceModel());
							// "生产厂家",
							row.getCell(colIndex++).setCellValue(equipment.getProducer());
							// "技术特征",
							row.getCell(colIndex++).setCellValue(equipment.getTechnology());
							// "单位",
							row.getCell(colIndex++).setCellValue(equipment.getMeasureUnit());
							// "数量",
							row.getCell(colIndex++).setCellValue(equipment.getAmount());
							// "出厂日期",
							if (equipment.getProductionDate() == null) {
								row.getCell(colIndex++).setCellValue("");
							} else {
								row.getCell(colIndex++).setCellValue(equipment.getProductionDate());
							}
							// "出厂编号",
							row.getCell(colIndex++).setCellValue(equipment.getFactoryNumber());
							// "购买日期",
							if (equipment.getProductionDate() == null) {
								row.getCell(colIndex++).setCellValue("");
							} else {
								row.getCell(colIndex++).setCellValue(equipment.getBuyDate());
							}
							// "使用日期",
							if (equipment.getProductionDate() == null) {
								row.getCell(colIndex++).setCellValue("");
							} else {
								row.getCell(colIndex++).setCellValue(equipment.getUseDate());
							}
							// "使用年限",
							row.getCell(colIndex++).setCellValue(equipment.getServiceLife());
							// "在籍",
							row.getCell(colIndex++).setCellValue(equipment.getInMembership());
							// "使用",
							row.getCell(colIndex++).setCellValue(equipment.getInUse());
							// "备用",
							row.getCell(colIndex++).setCellValue(equipment.getIsSpare());
							// "闲置",
							row.getCell(colIndex++).setCellValue(equipment.getIsIdle());
							// "待修",
							row.getCell(colIndex++).setCellValue(equipment.getNeedsRepair());
							// "待报废",
							row.getCell(colIndex++).setCellValue(equipment.getPrepareScrapped());
							// "已报废",
							row.getCell(colIndex++).setCellValue(equipment.getScrapped());
							// "借入",
							if (equipment.getBorrowed() == null) {
								row.getCell(colIndex++).setCellValue("");
							} else {
								row.getCell(colIndex++).setCellValue(equipment.getBorrowed());
							}
							// "借出",
							row.getCell(colIndex++).setCellValue(equipment.getIsLoan());
							// "主要附机",
							row.getCell(colIndex++).setCellValue(equipment.getAttachedDevice());
							// "原值",
							row.getCell(colIndex++).setCellValue(equipment.getOriginalValue());
							// "净值",
							row.getCell(colIndex++).setCellValue(equipment.getNetWorth());
							// "使用地点",
							row.getCell(colIndex++).setCellValue(equipment.getUsePlace());
							// "是否防爆"
							row.getCell(colIndex++).setCellValue(equipment.getExplosionProof());

						}
					}

				});
	}

	@RequestMapping(value = "/electr/equipment/equipment-ledger/upload", method = RequestMethod.POST)
	public void upload(@RequestParam MultipartFile file, String callBackFunction, HttpSession httpSession,
			HttpServletResponse response, final String uploadPath) throws IOException {
		// 生成文件路径
		String filePath = generatePath(file);

		String defaultUploadPath = null;
		// 自定义上传目录
		if (!Strings.isNullOrEmpty(uploadPath)) {
			if (uploadPath.charAt(uploadPath.length() - 1) != '/') {
				defaultUploadPath = uploadPath + '/';
			} else {
				defaultUploadPath = uploadPath;
			}
		} else {
			defaultUploadPath = UploadController.DEFAULT_PATH;
		}

		// 获取上传目录的真实路径
		String uploadRealPath = httpSession.getServletContext().getRealPath(defaultUploadPath);
		filePath = replaceChars(filePath);
		// 写入文件
		final File newFile = new File(uploadRealPath + "/" + filePath);
		FileUtils.writeByteArrayToFile(newFile, file.getBytes());
		try {
			equipmentLedgerService.importFormExcel(newFile.getAbsolutePath());
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 设置响应
		response.setContentType("text/html");
		response.getWriter().write(
				"<script>parent."
						+ callBackFunction
						+ "("
						+ JSON.toJSONString(new Response(new String(
								(defaultUploadPath.replaceFirst("/WEB-INF", "") + filePath)))) + ")</script>");
		response.flushBuffer();
	}

	private String generatePath(MultipartFile file) {
		Timestamp now = new Timestamp(System.currentTimeMillis());

		// 根据年月生成文件夹
		String folder = new SimpleDateFormat("yyyy-MM").format(now);

		// 重新生成文件名，文件名+日期+时间+6位随机数
		String fullFileName = file.getOriginalFilename();

		String fileName, type = "";
		if (fullFileName.lastIndexOf(".") != -1) {
			fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
			type = fullFileName.substring(fullFileName.lastIndexOf("."));
		} else {
			fileName = fullFileName;
		}

		String newFullFileName = fileName + "-" + new SimpleDateFormat("ddHHmmss").format(now) + "-"
				+ RandomStringUtils.randomNumeric(6) + type.toLowerCase();

		return folder + "/" + newFullFileName;
	}

	private String replaceChars(String srcString) {
		return srcString.replace("[", "【").replace("]", "】");
	}
}