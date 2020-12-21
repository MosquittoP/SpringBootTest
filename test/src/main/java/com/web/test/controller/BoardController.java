package com.web.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.test.model.Board;
import com.web.test.service.BoardService;
import com.web.test.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("board")
public class BoardController {
	
	XSSFWorkbook workbook = null;
	XSSFSheet sheet = null;
	XSSFRow row = null;
	XSSFCell cell = null;
	int rowno = 0;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@GetMapping
	@ApiOperation(value = "모든 게시글 목록 조회")
	public ResponseEntity<List<Board>> BoardList() {
		logger.debug("BoardList");
		List<Board> list = boardService.getBoard();
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "게시글 작성")
	public ResponseEntity<String> writeBoard(@RequestBody Board board) {
		logger.debug("writeBoard");
		Date date = new Date(System.currentTimeMillis());
		board.setDate(date);
		if (boardService.insert(board)) {
			int boardno = boardService.getBoardnoByBoard(board);
			board.setBoardno(boardno);
			File file = new File("board.xlsx");
			InputStream inp;
			if (!file.exists()) {
				try {
					FileOutputStream stream = new FileOutputStream(file);
					workbook = new XSSFWorkbook();
					sheet = workbook.createSheet("board");
					CellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy/m/d/ h:mm"));
					row = sheet.createRow(0);
					cell = row.createCell(0);
					cell.setCellValue(board.getBoardno());
					cell = row.createCell(1);
					cell.setCellValue(board.getUserno());
					cell = row.createCell(2);
					cell.setCellValue(board.getTitle());
					cell = row.createCell(3);
					cell.setCellValue(board.getContent());
					cell = row.createCell(4);
					cell.setCellValue(board.getDate());
					cell.setCellStyle(cellStyle);
					cell = row.createCell(5);
					cell.setCellValue("작성");
					try {
						workbook.write(stream);
						workbook.close();
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					inp = new FileInputStream(file);
					try {
						workbook = new XSSFWorkbook(inp);
						sheet = workbook.getSheet("board");
						if (sheet == null)
							sheet = workbook.createSheet("board");
						CellStyle cellStyle = workbook.createCellStyle();
						cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy/m/d h:mm"));
						rowno = sheet.getLastRowNum() + 1;
						row = sheet.createRow(rowno);
						cell = row.createCell(0);
						cell.setCellValue(board.getBoardno());
						cell = row.createCell(1);
						cell.setCellValue(board.getUserno());
						cell = row.createCell(2);
						cell.setCellValue(board.getTitle());
						cell = row.createCell(3);
						cell.setCellValue(board.getContent());
						cell = row.createCell(4);
						cell.setCellValue(board.getDate());
						cell.setCellStyle(cellStyle);
						cell = row.createCell(5);
						cell.setCellValue("작성");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				try {
					FileOutputStream stream = new FileOutputStream(file);
					try {
						workbook.write(stream);
						stream.close();
						workbook.close();
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String> (FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("{boardno}")
	@ApiOperation(value = "해당 게시글 조회")
	public ResponseEntity<Board> boardDetail(@PathVariable int boardno) {
		logger.debug("readBoard");
		Board board = boardService.getBoardByBoardno(boardno);
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	@PutMapping("{boardno}")
	@ApiOperation(value = "게시글 수정")
	public ResponseEntity<String> updateBoard(@RequestBody Board board) {
		logger.debug("updateBoard");
		Date date = new Date(System.currentTimeMillis());
		board.setDate(date);
		if (boardService.update(board)) {
			int boardno = boardService.getBoardnoByBoard(board);
			board.setBoardno(boardno);
			File file = new File("board.xlsx");
			try {
				InputStream inp = new FileInputStream(file);
				try {
					workbook = new XSSFWorkbook(inp);
					sheet = workbook.getSheet("board");
					if (sheet == null)
						sheet = workbook.createSheet("board");
					CellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy/m/d h:mm"));
					rowno = sheet.getLastRowNum() + 1;
					row = sheet.createRow(rowno);
					cell = row.createCell(0);
					cell.setCellValue(board.getBoardno());
					cell = row.createCell(1);
					cell.setCellValue(board.getUserno());
					cell = row.createCell(2);
					cell.setCellValue(board.getTitle());
					cell = row.createCell(3);
					cell.setCellValue(board.getContent());
					cell = row.createCell(4);
					cell.setCellValue(board.getDate());
					cell.setCellStyle(cellStyle);
					cell = row.createCell(5);
					cell.setCellValue("수정");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			try {
				FileOutputStream stream = new FileOutputStream(file);
				try {
					workbook.write(stream);
					workbook.close();
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String> (FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("{boardno}")
	@ApiOperation(value = "게시글 삭제")
	public ResponseEntity<String> deleteBoard(@PathVariable int boardno) {
		logger.debug("deleteBoard");
		if (boardService.delete(boardno))
			return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String> (FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("download")
	@ApiOperation(value = "게시글 파일 다운로드")
	public Object downloadBoard(HttpServletResponse response) {
		logger.debug("downloadBoard");
		File file = new File("board.xlsx");
		if (file.exists()) {
			InputStream inp = null;
			try {
				inp = new FileInputStream(file);
				try {
					workbook = new XSSFWorkbook(inp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//			response.setContentType("application/msexcel");
//			response.setHeader("Content-Type", "application/octet-stream");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			try {
				FileCopyUtils.copy(inp, response.getOutputStream());
				response.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			try {
//				response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(file.getName(), "UTF-8") + "\";charset=\"UTF-8\"");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			response.setHeader("Content-Disposition", "JSP Generated Data");
//			response.setHeader("Content-Type", "application/vnd.ms-excel");
//			response.setHeader("Content-Transfer-Encoding", "binary");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
	}
	
}
