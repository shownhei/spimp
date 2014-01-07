package cn.ccrise.spimp.electr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.spimp.electr.entity.AnnualOil;
import cn.ccrise.spimp.electr.entity.MaterialsPlan;
import cn.ccrise.spimp.electr.service.KilometerStaticsService;
import cn.ccrise.spimp.util.DateUtil;
import cn.ccrise.spimp.util.ExcelCallBackInteface;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * 年度统计、月度统计公里
 * 
 * @author zpf
 * 
 */

@Controller
public class KilometerStaticsController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private KilometerStaticsService kilometerStaticsService;

	@RequestMapping(value = "/electr/car/annual-kilometer/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, int year) throws Exception {
		HashMap<String, Object> root = new HashMap<String, Object>();
		kilometerStaticsService.annualOil(year, root);
		ExcelHelper<MaterialsPlan> helper = new ExcelHelper<MaterialsPlan>();
		helper.genExcelWithTel(httpSession, response, "electr/annual-kilometer.xls", root, "导出文档",
				new String[] { "材料计划" });
	}

	/**
	 * 年度运行情况
	 * 
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/electr/car/annual-kilometer/result", method = RequestMethod.GET)
	public ModelAndView getAnnualOil(Integer year) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		kilometerStaticsService.annualOil(year, root);
		return new ModelAndView("electr/car/annual-kilometer/result", root);
	}

	/**
	 * 年度运行情况
	 * 
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/electr/car/annual-kilometer/export", method = RequestMethod.GET)
	public void getAnnualOilExport(HttpSession httpSession, HttpServletResponse response, Integer year) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		kilometerStaticsService.annualOil(year, root);
		new ExcelHelper<AnnualOil>().genExcelWithTel(httpSession, response, "electr/car/annual-kilometer.xls", root,
				year + "无轨胶轮车行程公里一览表", new String[] { year + "无轨胶轮车行程公里一览表" }, new ExcelCallBackInteface() {
					@Override
					public void process(Workbook book, HashMap<String, Object> root) {
					}
				});
	}

	/**
	 * 月度运行情况
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "/electr/car/monthly-run/result", method = RequestMethod.GET)
	public ModelAndView getMonthlyRun(Long carId, Integer year, Integer month) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		kilometerStaticsService.monthlyRunQuery(carId, year, month, root);
		return new ModelAndView("electr/car/monthly-run/result", root);
	}
	/**
	 * 月度运行情况
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "/electr/car/monthly-run/export", method = RequestMethod.GET)
	public void monthlyRunExport(HttpSession httpSession, HttpServletResponse response,Long carId, Integer year, Integer month) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		kilometerStaticsService.monthlyRunQuery(carId, year, month, root);
		
		new ExcelHelper<MaterialsPlan>().genExcelWithTel(httpSession, response, "electr/car/monthly-run.xls", root,
				"月度运行统计", new String[] { "运行情况统计表" },new ExcelCallBackInteface(){

			        private void drawCarListHeader(Sheet sheet,List<String> carList,CellStyle cellStyle){
			        	Iterator<String> carIt = carList.iterator();
						String carNo=null;
						int carIndex=0;
						while(carIt.hasNext()){
							carNo=carIt.next();
							sheet.addMergedRegion(new CellRangeAddress(1, 1,  
									carIndex*10+1, (carIndex+1)*10));  
							carIndex++;
						}
						carIndex=0;
						carIt = carList.iterator();
						while(carIt.hasNext()){
							carNo=carIt.next();
							Cell cell=sheet.getRow(1).getCell(carIndex*10+1);
							cell.setCellValue(carNo);
							cell.setCellStyle(cellStyle);
							carIndex++;
						}
			        }
			        /**
			         * 绘制班次
			         */
			        private void drawClasss(Sheet sheet,List<String> carList,CellStyle cellStyle){
						int carIndex=0;
						
						Iterator<String> carIt = carList.iterator();
						int offset=1;
						carIndex=0;
						Cell cell = null;
						while(carIt.hasNext()){
							carIt.next();
							offset=carIndex*10+1;
							sheet.addMergedRegion(new CellRangeAddress(2, 2,  
									offset, offset+2));  
							sheet.addMergedRegion(new CellRangeAddress(2, 2,  
									offset+3, offset+5)); 
							sheet.addMergedRegion(new CellRangeAddress(2, 2,  
									offset+6, offset+8));  
							cell=sheet.getRow(2).getCell(carIndex*10+1);
							cell.setCellStyle(cellStyle);
							cell.setCellValue("零点");
							cell=sheet.getRow(2).getCell(carIndex*10+4);
							cell.setCellStyle(cellStyle);
							cell.setCellValue("八点");
							cell=sheet.getRow(2).getCell(carIndex*10+7);
							cell.setCellStyle(cellStyle);
							cell.setCellValue("四点");
							carIndex++;
						}
			        }
			        /**
			         * 绘制车次	路程	加油
			         */
			        private void drawDataType(Sheet sheet,List<String> carList,CellStyle cellStyle){
						int carIndex=0;
						Iterator<String> carIt = carList.iterator();
						int offset=1;
						carIndex=0;
						Cell cell = null;
						final int datTypeTitleRowIndex=3;
						String dataTypeTitleArray[]={"车次","路程","加油"};
						int dataTypeTitleCount=dataTypeTitleArray.length;
						while(carIt.hasNext()){
							carIt.next();
							offset=carIndex*10+1;
							for(int classIndex=0;classIndex<3;classIndex++){
								for(int titleIndex=0;titleIndex<dataTypeTitleCount;titleIndex++){
									cell=sheet.getRow(datTypeTitleRowIndex).getCell(offset+classIndex*dataTypeTitleCount+titleIndex);
									cell.setCellStyle(cellStyle);
									cell.setCellValue(dataTypeTitleArray[titleIndex]);
								}
							}
							carIndex++;
						}
			        }
					@Override
					public void process(Workbook book,
							HashMap<String, Object> root) {
						Sheet sheet=book.getSheetAt(0);
						CellStyle privateCellStyle_left=sheet.getRow(0).getCell(0).getCellStyle();
						CellStyle privateCellStyle_center=sheet.getRow(0).getCell(1).getCellStyle();
						CellStyle privateCellStyle_normal=sheet.getRow(0).getCell(2).getCellStyle();
						@SuppressWarnings("unchecked")
						ArrayList<ArrayList<Long>> result=(ArrayList<ArrayList<Long>>) root.get("result");
						@SuppressWarnings("unchecked")
						List<String> carList= (List<String> )root.get("carList");
						 int cols=0;
						if(result.size()>0){
							cols=result.get(0).size();
						}
						final int resultListSize=result.size();
						int startrow=4;
						int startcol=1;
						Cell cell= null;
						/**
						 * 主要数据区域
						 */
						drawMainDataArea(sheet, privateCellStyle_center,
								privateCellStyle_normal, result, cols,
								resultListSize, startrow, startcol);
						//设置月份
						for(int rowIndex=0;rowIndex<resultListSize+startrow;rowIndex++){
							Row row=sheet.getRow(rowIndex);
							if(rowIndex>=startrow){
								cell=row.createCell(0);//
								cell.setCellStyle(privateCellStyle_left);
								cell.setCellValue((rowIndex-startrow+1)+"日");
							}
						}//
						int sumColumnSize=0;
						if(result.size()>0){
							sumColumnSize+=result.get(0).size();
						}
						CellRangeAddress temp = new CellRangeAddress(0, 0,
								0, sumColumnSize);
						sheet.addMergedRegion(temp);
						
						//所有车辆
						Date currentDate = new Date();
						sheet.getRow(0).getCell(0).setCellValue(DateUtil.formateDate(currentDate, "yyyy年MM月份")+"运行情况统计表");
						sheet.getRow(0).getCell(0).setCellStyle(privateCellStyle_center);
						this.drawCarListHeader(sheet, carList,privateCellStyle_center);
						this.drawClasss(sheet, carList,privateCellStyle_center);
						this.drawDataType(sheet, carList, privateCellStyle_center);
						sheet.getRow(2).getCell(0).setCellValue("班次");
						sheet.getRow(3).getCell(0).setCellValue("日期");
						//合计行
						drawSumRow(sheet, cols, resultListSize, startrow);
					}
					/**
					 * 主要数据区域
					 * @param sheet
					 * @param privateCellStyle_center
					 * @param privateCellStyle_normal
					 * @param result
					 * @param cols
					 * @param resultListSize
					 * @param startrow
					 * @param startcol
					 */
					private void drawMainDataArea(Sheet sheet,
							CellStyle privateCellStyle_center,
							CellStyle privateCellStyle_normal,
							ArrayList<ArrayList<Long>> result, int cols,
							final int resultListSize, int startrow, int startcol) {
						Cell cell;
						for(int rowIndex=0;rowIndex<resultListSize+startrow;rowIndex++){
							Row row=sheet.createRow(rowIndex);
							for(int colIndex=0;colIndex<cols+startcol;colIndex++){
								row.createCell(colIndex).setCellStyle(privateCellStyle_center);
							}
							if(rowIndex>=startrow){
								ArrayList<Long> raw=result.get(rowIndex-startrow);
								for(int colIndex=0;colIndex<cols+startcol;colIndex++){
									if(colIndex>=startcol){
										cell=row.createCell(colIndex);
										cell.setCellStyle(privateCellStyle_normal);
										if(raw.get(colIndex-startcol)!=0){
											cell.setCellValue(raw.get(colIndex-startcol));	
										}
									}
								}
							}
						}//
					}
					/**
					 * 合计行
					 * @param sheet
					 * @param cols
					 * @param resultListSize
					 * @param startrow
					 */
					private void drawSumRow(Sheet sheet, int cols,
							final int resultListSize, int startrow) {
						Cell cell;
						int sumRowIndex=resultListSize+startrow;
						char startChar='B';
						Row sumRow=sheet.createRow(sumRowIndex);
						sumRow.createCell(0).setCellValue("合计");
						for(int colIndex=1;colIndex<cols;colIndex++){
							cell=sumRow.createCell(colIndex);
							cell.setCellFormula("sum("+startChar+sumRowIndex+":"+startChar+(startrow+1)+")");
						   startChar+=1;
						}
					}});
	}

}
