package com.spacehub.www.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ReservationVO;

public class ExcelDownloadAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 이미 커밋된 경우 처리
        if (resp.isCommitted()) {
            throw new IllegalStateException("Response is already committed, cannot write excel data.");
        }

        try (Workbook workbook = new XSSFWorkbook(); 
        		OutputStream out = resp.getOutputStream()) {
            Sheet sheet = workbook.createSheet("Reservation List");

            // 엑셀 헤더 생성
            Row headerRow = sheet.createRow(0);
            String[] headers = {"예약번호", "체크인", "체크아웃", "이름", "전화번호", "가격", "게스트", "할인", "방번호", "예약상태", "예약자번호", "IP", "예약날짜"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // 엑셀 데이터 생성
            ReservationDAO dao = new ReservationDAO();
            List<ReservationVO> list = dao.getAll();
            System.out.println(list);
            int rowNum = 1;
            for (ReservationVO vo : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(vo.getReservno());
                row.createCell(1).setCellValue(vo.getCheckin());
                row.createCell(2).setCellValue(vo.getCheckout());
                row.createCell(3).setCellValue(vo.getName());
                row.createCell(4).setCellValue(vo.getPhone());
                row.createCell(5).setCellValue(vo.getPrice());
                row.createCell(6).setCellValue(vo.getGuest());
                row.createCell(7).setCellValue(vo.getDcratio());
                row.createCell(8).setCellValue(vo.getSpaceno());
                row.createCell(9).setCellValue(vo.getStatus());
                row.createCell(10).setCellValue(vo.getMemno());
                row.createCell(11).setCellValue(vo.getIp());
                row.createCell(12).setCellValue(vo.getRegdate());
            }

            // 엑셀 파일 다운로드
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename=" + generateFileName() + ".xlsx");

            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            // 예외 처리 및 클라이언트에게 알림
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Failed to generate excel file. Please try again later.");
        }
        return "admin/reservationList.jsp";
    }

    // 파일명을 현재 날짜와 시간으로 생성하는 메소드
    private String generateFileName() {
        // 파일명에 사용할 수 없는 문자를 언더스코어로 대체
        return "ReservationList_" + System.currentTimeMillis();
    }
}
