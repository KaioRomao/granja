package br.com.precojusto.granja.config.exception.utils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class ExcelUtil {

    public SXSSFWorkbook wb;
    public CellStyle cellStyleInteger = null;
    public CellStyle cellStyleValor = null;
    public CellStyle cellStyleDate = null;
    public CellStyle cellStyleDateSimples = null;
    public XSSFCellStyle cellTituloStyle = null;
    public XSSFCellStyle cellStyle = null;
    public XSSFCellStyle cellStyleBorder = null;
    public XSSFCellStyle fontBoldStyle = null;

    public ExcelUtil(SXSSFWorkbook wb) {
        DataFormat format = wb.createDataFormat();
        this.wb = wb;

        cellStyleDate = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));

        cellStyleDateSimples = wb.createCellStyle();
        cellStyleDateSimples.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        cellStyleValor = wb.createCellStyle();
        cellStyleValor.setDataFormat(format.getFormat("_(#,##0.00_);_((#,##0.00);_(\"-\"??_);_(@_)"));

        cellStyleInteger = wb.createCellStyle();
        cellStyleInteger.setDataFormat(format.getFormat("0"));
    }

    public SXSSFCell addCell(int coluna, SXSSFRow row, String texto) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(texto);
        return cell;
    }

    public SXSSFCell addCellQuebraLinha(int coluna, SXSSFRow row, String texto, boolean stBordas) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(texto);

        if (texto.contains("\n") || texto.isEmpty()) {
            XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
            style.setWrapText(true);

            if (stBordas) {
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);

                short black = IndexedColors.BLACK.getIndex();
                style.setLeftBorderColor(black);
                style.setRightBorderColor(black);
                style.setTopBorderColor(black);
                style.setBottomBorderColor(black);
            }

            cell.setCellStyle(style);
        }
        return cell;
    }

    public SXSSFCell addCellBordas(int coluna, SXSSFRow row, String texto) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        if (cellStyleBorder == null) {
            cellStyleBorder = (XSSFCellStyle) wb.createCellStyle();
            cellStyleBorder.setBorderLeft(BorderStyle.THIN);
            cellStyleBorder.setBorderRight(BorderStyle.THIN);
            cellStyleBorder.setBorderTop(BorderStyle.THIN);
            cellStyleBorder.setBorderBottom(BorderStyle.THIN);
            cellStyleBorder.setAlignment(HorizontalAlignment.CENTER);
            cellStyleBorder.setVerticalAlignment(VerticalAlignment.TOP);

            short black = IndexedColors.BLACK.getIndex();
            cellStyleBorder.setLeftBorderColor(black);
            cellStyleBorder.setRightBorderColor(black);
            cellStyleBorder.setTopBorderColor(black);
            cellStyleBorder.setBottomBorderColor(black);
        }
        cell.setCellStyle(cellStyleBorder);
        cell.setCellValue(texto);
        return cell;
    }

    public SXSSFCell addCellTitulo(int coluna, SXSSFRow row, String texto) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(texto);
        return cell;
    }

    public SXSSFCell addCellTituloFontBold(int coluna, SXSSFRow row, String texto) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(texto);
        if (fontBoldStyle == null) {
            fontBoldStyle = (XSSFCellStyle) wb.createCellStyle();
            XSSFFont font = (XSSFFont) wb.createFont();
            font.setBold(true);
            fontBoldStyle.setFont(font);
        }
        cell.setCellStyle(fontBoldStyle);
        return cell;
    }

    public SXSSFCell addCellTituloStyle(int coluna, SXSSFRow row, String texto) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(texto);

        if (cellTituloStyle == null) {
            cellTituloStyle = (XSSFCellStyle) wb.createCellStyle();

            // POI 5.x: use java.awt.Color no XSSFColor
            cellTituloStyle.setFillForegroundColor(new XSSFColor(new Color(192, 192, 192), null));
            cellTituloStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cellTituloStyle.setBorderLeft(BorderStyle.THIN);
            cellTituloStyle.setBorderRight(BorderStyle.THIN);
            cellTituloStyle.setBorderTop(BorderStyle.THIN);
            cellTituloStyle.setBorderBottom(BorderStyle.THIN);

            short black = IndexedColors.BLACK.getIndex();
            cellTituloStyle.setLeftBorderColor(black);
            cellTituloStyle.setRightBorderColor(black);
            cellTituloStyle.setTopBorderColor(black);
            cellTituloStyle.setBottomBorderColor(black);

            cellTituloStyle.setAlignment(HorizontalAlignment.CENTER);
            cellTituloStyle.setVerticalAlignment(VerticalAlignment.TOP);
        }

        cell.setCellStyle(cellTituloStyle);
        return cell;
    }

    public SXSSFCell addCellStyle(int coluna, SXSSFRow row, String texto) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(texto);

        if (cellStyle == null) {
            cellStyle = (XSSFCellStyle) wb.createCellStyle();
            if (texto.contains("\n")) {
                cellStyle.setWrapText(true);
            }

            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);

            short black = IndexedColors.BLACK.getIndex();
            cellStyle.setLeftBorderColor(black);
            cellStyle.setRightBorderColor(black);
            cellStyle.setTopBorderColor(black);
            cellStyle.setBottomBorderColor(black);

            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        cell.setCellStyle(cellStyle);
        return cell;
    }

    public SXSSFCell addCellStyleForcaQuebraLinha(int coluna, SXSSFRow row, String texto) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(texto);

        XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);

        short black = IndexedColors.BLACK.getIndex();
        style.setLeftBorderColor(black);
        style.setRightBorderColor(black);
        style.setTopBorderColor(black);
        style.setBottomBorderColor(black);

        cell.setCellStyle(style);
        return cell;
    }

    public SXSSFCell addCellQuebraLinhaCenter(int coluna, SXSSFRow row, String texto) throws IOException {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(texto);

        XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);

        short black = IndexedColors.BLACK.getIndex();
        style.setLeftBorderColor(black);
        style.setRightBorderColor(black);
        style.setTopBorderColor(black);
        style.setBottomBorderColor(black);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);

        cell.setCellStyle(style);
        return cell;
    }

    public SXSSFCell addCellDateDefault(Integer coluna, SXSSFRow row, Date data) {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(data);
        cell.setCellStyle(cellStyleDate);
        return cell;
    }

    public SXSSFCell addCellDateSimples(Integer coluna, SXSSFRow row, Date data) {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(data);
        cell.setCellStyle(cellStyleDateSimples);
        return cell;
    }

    public SXSSFCell addCellValor(Integer coluna, SXSSFRow row, Double valor) {
        SXSSFCell cell = row.createCell(coluna);
        cell.setCellValue(valor);
        cell.setCellStyle(cellStyleValor);
        return cell;
    }

    public SXSSFCell addCellValor(Integer coluna, SXSSFRow row, Object valor) {
        SXSSFCell cell = row.createCell(coluna);
        if (valor == null) {
            cell.setCellValue(0.00);
        } else {
            Number n = (Number) valor;
            cell.setCellValue(n.doubleValue());
        }
        cell.setCellStyle(cellStyleValor);
        return cell;
    }

    public SXSSFCell addCellValorInteger(Integer coluna, SXSSFRow row, Object valor) {
        SXSSFCell cell = row.createCell(coluna);
        if (valor == null) {
            cell.setCellValue(0.00);
        } else {
            Number n = (Number) valor;
            cell.setCellValue(n.doubleValue());
        }
        cell.setCellStyle(cellStyleInteger);
        return cell;
    }
}