/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.AnnualOil;
import cn.ccrise.spimp.electr.entity.BrakeDevice;
import cn.ccrise.spimp.electr.entity.ElectromotorDevice;
import cn.ccrise.spimp.electr.entity.ReducerDevice;
import cn.ccrise.spimp.electr.entity.TensioningDevice;
import cn.ccrise.spimp.electr.entity.TransformEquipment;
import cn.ccrise.spimp.electr.service.BrakeDeviceService;
import cn.ccrise.spimp.electr.service.ElectromotorDeviceService;
import cn.ccrise.spimp.electr.service.ReducerDeviceService;
import cn.ccrise.spimp.electr.service.TensioningDeviceService;
import cn.ccrise.spimp.electr.service.TransformEquipmentService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelCallBackInteface;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * 运输设备台账。
 * 
 */
@Controller
public class TransformEquipmentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ReducerDeviceService reducerDeviceService;
	@Autowired
	private ElectromotorDeviceService electromotorDeviceService;
	@Autowired
	private BrakeDeviceService brakeDeviceService;
	@Autowired
	private TensioningDeviceService tensioningDeviceService;
	@Autowired
	private TransformEquipmentService transformEquipmentService;

	/**
	 * 数据导入
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/electr/equipment/transform-equipments/test", method = RequestMethod.GET)
	@ResponseBody
	public Response get(HttpSession httpSession) {
		String templateFoldPath = httpSession.getServletContext().getRealPath("/");
		String fileName=templateFoldPath + "/WEB-INF/resources/template/机电机运队设备统计台帐2013.10.xls";
		try {
			transformEquipmentService.importFormExcel(fileName);
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(true);
	}
	@RequestMapping(value = "/electr/equipment/transform-equipments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(transformEquipmentService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, Long deviceClass, String search)
			throws Exception {
		Page<TransformEquipment> page = new Page<TransformEquipment>();
		page.setPageSize(100000);
		page = transformEquipmentService.pageQuery(page, deviceClass, search);
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("result", page.getResult());
		root.put("yearMonth", new SimpleDateFormat("yyyy.MM").format(new Date(System.currentTimeMillis())));
		Iterator<TransformEquipment> equipmentIt = page.getResult().iterator();
		TransformEquipment temp = null;
		final HashMap<Long, List<ReducerDevice>> reducerMap = new HashMap<Long, List<ReducerDevice>>();
		final HashMap<Long, List<ElectromotorDevice>> electromotorMap = new HashMap<Long, List<ElectromotorDevice>>();
		final HashMap<Long, List<BrakeDevice>> brakeMap = new HashMap<Long, List<BrakeDevice>>();
		final HashMap<Long, List<TensioningDevice>> tensioningMap = new HashMap<Long, List<TensioningDevice>>();
		Long equipmentId = null;
		final HashMap<Long, Integer> maxRowMap = new HashMap<Long, Integer>();
		int maxRows = 0;
		int allRows = 0;
		while (equipmentIt.hasNext()) {
			temp = equipmentIt.next();
			equipmentId = temp.getId();
			SimpleExpression expression = Restrictions.eq("transformEquipmentId", equipmentId);

			List<ReducerDevice> reducerList = reducerDeviceService.find(expression);
			reducerMap.put(temp.getId(), reducerList);
			maxRows = reducerList.size();

			List<ElectromotorDevice> electromotorList = electromotorDeviceService.find(expression);
			electromotorMap.put(temp.getId(), electromotorList);
			maxRows = electromotorList.size() > maxRows ? electromotorList.size() : maxRows;

			List<BrakeDevice> brakeList = brakeDeviceService.find(expression);
			brakeMap.put(temp.getId(), brakeList);
			maxRows = brakeList.size() > maxRows ? brakeList.size() : maxRows;

			List<TensioningDevice> tensioningList = tensioningDeviceService.find(expression);
			tensioningMap.put(temp.getId(), tensioningList);
			maxRows = tensioningList.size() > maxRows ? tensioningList.size() : maxRows;
			if (maxRows == 0) {
				maxRows = 1;
			}
			maxRowMap.put(equipmentId, maxRows);
			allRows += maxRows;
		}
		final int maxRow = allRows;
		new ExcelHelper<AnnualOil>().genExcelWithTel(httpSession, response,
				"electr/equipment/transform-equipments.xls", root, "运输设备", new String[] { "运输设备" },
				new ExcelCallBackInteface() {
					@Override
					public void process(Workbook book, HashMap<String, Object> root) {
						int startRow = 3;
						int mainRecordCols = 12;// 主记录的所有列数
						/****** 配件列数 ********/
						int reducerCols = 5;// 减速机
						int electromotorCols = 4;// 电动机
						int brakeCols = 4;// 制动器
						int tensioningCols = 7;// 拉紧装置
						final int allCols = 1 + mainRecordCols + reducerCols + electromotorCols + brakeCols
								+ tensioningCols;

						Sheet sheet = book.getSheetAt(0);
						@SuppressWarnings("unchecked")
						List<TransformEquipment> result = (List<TransformEquipment>) root.get("result");
						for (int rowIndex = 0; rowIndex < maxRow; rowIndex++) {
							Row currentRow = sheet.createRow(rowIndex + startRow);
							for (int colIndex = 0; colIndex < allCols; colIndex++) {
								Cell cell = currentRow.createCell(colIndex);
								cell.setCellValue("");
							}
						}
						int mainStartRow = startRow;
						for (TransformEquipment equipment : result) {
							drawTensioning(tensioningMap, maxRowMap, mainRecordCols, reducerCols, electromotorCols,
									brakeCols, tensioningCols, sheet, result, equipment, mainStartRow);
							drawBrake(maxRowMap, mainRecordCols, reducerCols, electromotorCols, brakeCols,
									tensioningCols, sheet, result, equipment, mainStartRow);
							drawElectromotor(maxRowMap, mainRecordCols, reducerCols, electromotorCols, brakeCols,
									tensioningCols, sheet, result, equipment, mainStartRow);
							drawReducers(maxRowMap, mainRecordCols, reducerCols, electromotorCols, brakeCols,
									tensioningCols, sheet, result, equipment, mainStartRow);
							int equipmentStartRow = mainStartRow;
							int colIndex = 2;
							Row row = sheet.getRow(equipmentStartRow++);
							row.getCell(colIndex++).setCellValue(equipment.getDeviceClass().getItemName());
							row.getCell(colIndex++).setCellValue(equipment.getDeviceName());
							row.getCell(colIndex++).setCellValue(equipment.getDeviceModel());
							row.getCell(colIndex++).setCellValue(equipment.getSpeed());
							row.getCell(colIndex++).setCellValue(equipment.getEquipmentNumber());
							row.getCell(colIndex++).setCellValue(equipment.getProductionDate());
							row.getCell(colIndex++).setCellValue(equipment.getEquipmentNumber());
							row.getCell(colIndex++).setCellValue(equipment.getLocation());
							row.getCell(colIndex++).setCellValue(equipment.getProducer());
							row.getCell(colIndex++).setCellValue(equipment.getLayoutLength());

							mainStartRow += maxRowMap.get(equipment.getId());
						}
						int mainStartRow2 = startRow;
						for (TransformEquipment equipment : result) {
							int rows = maxRowMap.get(equipment.getId());
							int colIndex = 0;
							CellRangeAddress temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1,
									colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							temp = new CellRangeAddress(mainStartRow2, mainStartRow2 + rows - 1, colIndex, colIndex++);
							sheet.addMergedRegion(temp);
							mainStartRow2 += maxRowMap.get(equipment.getId());
						}

					}

					private void drawBrake(HashMap<Long, Integer> maxRowMap, int mainRecordCols, int reducerCols,
							int electromotorCols, int brakeCols, int tensioningCols, Sheet sheet,
							List<TransformEquipment> result, TransformEquipment equipment, int mainStartRow) {
						int brakeStartCol = mainRecordCols + reducerCols + electromotorCols;
						int reducerStartRow = mainStartRow;
						Cell tempCell = null;
						for (BrakeDevice brake : brakeMap.get(equipment.getId())) {
							Row row = sheet.getRow(reducerStartRow++);
							row.getCell(brakeStartCol).setCellValue(brake.getDeviceModel());
							tempCell = row.getCell(brakeStartCol);
							setComment(sheet, tempCell, brake.getDeviceModel());
							row.getCell(brakeStartCol + 1).setCellValue(brake.getFactoryNumber());
							row.getCell(brakeStartCol + 2).setCellValue(brake.getProductionDate());
							row.getCell(brakeStartCol + 3).setCellValue(brake.getProducer());
						}
						int rowCount = brakeMap.get(equipment.getId()).size();
						if (maxRowMap.get(equipment.getId()) > 1 && (rowCount == 0 || rowCount == 1)) {
							reducerStartRow = mainStartRow;
							mergeCells(maxRowMap, sheet, equipment, reducerStartRow, mainRecordCols + reducerCols
									+ electromotorCols, brakeCols);
						}
					}

					private void drawElectromotor(HashMap<Long, Integer> maxRowMap, int mainRecordCols,
							int reducerCols, int electromotorCols, int brakeCols, int tensioningCols, Sheet sheet,
							List<TransformEquipment> result, TransformEquipment equipment, int mainStartRow) {
						int electromotorStartCol = mainRecordCols + reducerCols;
						int electromotorStartRow = mainStartRow;
						Cell tempCell = null;
						for (ElectromotorDevice electromotor : electromotorMap.get(equipment.getId())) {
							Row row = sheet.getRow(electromotorStartRow++);
							row.getCell(electromotorStartCol).setCellValue(electromotor.getDeviceModel());
							tempCell = row.getCell(electromotorStartCol);
							setComment(sheet, tempCell, electromotor.getDeviceModel());

							row.getCell(electromotorStartCol + 1).setCellValue(electromotor.getFactoryNumber());
							row.getCell(electromotorStartCol + 2).setCellValue(electromotor.getProductionDate());
							row.getCell(electromotorStartCol + 3).setCellValue(electromotor.getProducer());
						}
						int rowCount = electromotorMap.get(equipment.getId()).size();
						if (maxRowMap.get(equipment.getId()) > 1 && (rowCount == 0 || rowCount == 1)) {
							electromotorStartRow = mainStartRow;
							mergeCells(maxRowMap, sheet, equipment, electromotorStartRow, mainRecordCols + reducerCols,
									electromotorCols);
						}
					}

					private void drawReducers(HashMap<Long, Integer> maxRowMap, int mainRecordCols, int reducerCols,
							int electromotorCols, int brakeCols, int tensioningCols, Sheet sheet,
							List<TransformEquipment> result, TransformEquipment equipment, int mainStartRow) {
						int reducerStartCol = mainRecordCols;
						int reducerStartRow = mainStartRow;
						Cell tempCell = null;
						for (ReducerDevice reducer : reducerMap.get(equipment.getId())) {
							Row row = sheet.getRow(reducerStartRow++);
							row.getCell(reducerStartCol).setCellValue(reducer.getDeviceModel());
							tempCell = row.getCell(reducerStartCol);
							setComment(sheet, tempCell, reducer.getDeviceModel());

							row.getCell(reducerStartCol + 1).setCellValue(reducer.getRunningPower());
							row.getCell(reducerStartCol + 2).setCellValue(reducer.getTransmissionRatio());
							row.getCell(reducerStartCol + 3).setCellValue(reducer.getFactoryNumber());
							row.getCell(reducerStartCol + 4).setCellValue(reducer.getProducer());
						}
						int rowCount = reducerMap.get(equipment.getId()).size();
						if (maxRowMap.get(equipment.getId()) > 1 && (rowCount == 0 || rowCount == 1)) {
							reducerStartRow = mainStartRow;
							mergeCells(maxRowMap, sheet, equipment, reducerStartRow, mainRecordCols, reducerCols);
						}
					}

					/**
					 * 拉紧装置
					 * 
					 * @param tensioningMap
					 * @param maxRowMap
					 * @param mainRecordCols
					 * @param reducerCols
					 * @param electromotorCols
					 * @param brakeCols
					 * @param sheet
					 * @param result
					 * @param equipment
					 * @param mainStartRow
					 */
					private void drawTensioning(HashMap<Long, List<TensioningDevice>> tensioningMap,
							HashMap<Long, Integer> maxRowMap, int mainRecordCols, int reducerCols,
							int electromotorCols, int brakeCols, int tensioningCols, Sheet sheet,
							List<TransformEquipment> result, TransformEquipment equipment, int mainStartRow) {
						int tensioningStartCol = mainRecordCols + reducerCols + electromotorCols + brakeCols;
						int tensioningStartRow = mainStartRow;
						Cell tempCell = null;
						for (TensioningDevice tensioning : tensioningMap.get(equipment.getId())) {
							Row row = sheet.getRow(tensioningStartRow++);
							row.getCell(tensioningStartCol).setCellValue(tensioning.getTakeUp());
							row.getCell(tensioningStartCol + 1).setCellValue(tensioning.getDeviceName());
							row.getCell(tensioningStartCol + 2).setCellValue(tensioning.getDeviceModel());
							row.getCell(tensioningStartCol + 3).setCellValue(tensioning.getDeviceNumber());
							if(tensioning.getProductionDate()!=null){
								row.getCell(tensioningStartCol + 4).setCellValue(tensioning.getProductionDate());
							}
							row.getCell(tensioningStartCol + 5).setCellValue(tensioning.getProducer());
							row.getCell(tensioningStartCol + 6).setCellValue("备注");
							tempCell = row.getCell(tensioningStartCol + 6);
							setComment(sheet, tempCell, tensioning.getTechParameters());
						}
						int rowCount = tensioningMap.get(equipment.getId()).size();
						if (maxRowMap.get(equipment.getId()) > 1 && (rowCount == 0 || rowCount == 1)) {
							tensioningStartRow = mainStartRow;
							mergeCells(maxRowMap, sheet, equipment, tensioningStartRow, tensioningStartCol,
									tensioningCols);
						}
					}

					private void mergeCells(HashMap<Long, Integer> maxRowMap, Sheet sheet,
							TransformEquipment equipment, int startRow, int startCol, int cols) {
						for (int i = 0; i < cols; i++) {
							CellRangeAddress temp = new CellRangeAddress(startRow, startRow
									+ maxRowMap.get(equipment.getId()) - 1, startCol + i, startCol + i);
							sheet.addMergedRegion(temp);
						}
					}

					private void setComment(Sheet sheet, Cell tempCell, String value) {
						Comment comment = sheet.createDrawingPatriarch().createCellComment(
								new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 1, (short) 5, 6));
						comment.setAuthor("赵培夫");
						comment.setString(new HSSFRichTextString(value));
						tempCell.setCellComment(comment);
					}

				});

	}

	@RequestMapping(value = "/electr/equipment/transform-equipments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(transformEquipmentService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments/device", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getDevicesInfo(Long id) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		// * (1):减速机-ReducerDevice;
		// * (2):电动机-ElectromotorDevice;
		// * (3):制动器-BrakeDevice;
		// * (4):拉紧装置-TensioningDevice;
		root.put("reducerDevices", reducerDeviceService.find(Restrictions.eq("transformEquipmentId", id)));
		root.put("electromotorDevices", electromotorDeviceService.find(Restrictions.eq("transformEquipmentId", id)));
		root.put("brakeDeviceDevices", brakeDeviceService.find(Restrictions.eq("transformEquipmentId", id)));
		root.put("tensioningDevices", tensioningDeviceService.find(Restrictions.eq("transformEquipmentId", id)));
		return new ModelAndView("electr/equipment/transformequipment/result", root);
	}

	@RequestMapping(value = "/electr/equipment/transform-equipment", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/transformequipment/index";
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<TransformEquipment> page, Long deviceClass, String search) {
		page = transformEquipmentService.pageQuery(page, deviceClass, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments", method = RequestMethod.POST)
	@ResponseBody
	public Response save(HttpSession httpSession, @Valid @RequestBody TransformEquipment transformEquipment) {

		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		transformEquipment.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(transformEquipmentService.save(transformEquipment));
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(HttpSession httpSession, @Valid @RequestBody TransformEquipment transformEquipment,
			@PathVariable long id) {

		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		transformEquipment.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(transformEquipmentService.update(transformEquipment));
	}
}