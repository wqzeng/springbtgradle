package com.wqzeng.springbtgradle.util;

import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.impl.StreamingRow;
import com.wqzeng.springbtgradle.annotation.ExcelMapper;
import com.wqzeng.springbtgradle.exception.BizException;
import com.wqzeng.springbtgradle.pattern.iterator.SelectIterator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ExcelUtils {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
	public static final String FILE_SUFFIX = ".xlsx";

	public static <T> File writeFile(Iterator<List<T>> iterator, ExcelFileBuilder<T> builder) {
		return writeFile(iterator, builder.getHeader(), builder.getRowBuilder());
	}

	public static <T> File writeFile(Iterator<List<T>> iterator, String[] header, ExcelUtils.RowBuilder<T> builder) {
		return writeFile(iterator, header, builder, UUID.randomUUID().toString());
	}

	public static <T> File writeFile (Iterator<List<T>> iterator, String[] header, ExcelUtils.RowBuilder<T> builder, String fileName) {
		FileOutputStream bos = null;
		SXSSFWorkbook workbook = null;
		try {
			File uploadDir = new File("/apps/dat");
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			String tmpFileName = fileName + FILE_SUFFIX;
			File tmpFile = new File(uploadDir, tmpFileName);
			bos = new FileOutputStream(tmpFile);

			workbook = new SXSSFWorkbook(1000);
			if (iterator.hasNext()) {
				ExcelUtils.fillWorkbook(iterator.next(), header, builder, workbook);
			}
			while (iterator.hasNext()) {
				ExcelUtils.fillWorkbook(iterator.next(), null, builder, workbook);
			}
			workbook.write(bos);
			return tmpFile;
		} catch (Exception e){
			logger.error("生成excel文件失败", e);
			throw new BizException("处理文件失败, 请联系系统管理员");
		} finally {
			closeQuietly(workbook);
			IOUtils.closeQuietly(bos);
		}
	}

	public static void deleteFile(File tmpFile) {
		if (tmpFile == null) {
			return;
		}
		if (!tmpFile.getName().toLowerCase().endsWith(FILE_SUFFIX)) {
			logger.error("删除文件失败, 只允许删除 {} 结尾的文件. fileName:{}", FILE_SUFFIX, tmpFile.getAbsoluteFile());
			return;
		}
		boolean result = tmpFile.delete();
		logger.info("删除文件:{}  删除结果:{}", tmpFile.getAbsoluteFile(), result);
	}

	public static void closeQuietly(SXSSFWorkbook workbook) {
		if (workbook == null) {
			return;
		}
		try {
			workbook.dispose();
			workbook.close();
		} catch (Exception e) {
			logger.error("Failed to close workbook", e);
		}
	}

	public static <T> void fillWorkbook(Collection<T> objects, String[] header, RowBuilder<T> rowBuilder, Workbook workbook) {
        if (null == workbook) {
            throw new BizException("导出Excel文件异常 入参异常!");
        }

		try {
            Sheet sheet = workbook.getSheet("sheet");
            if (sheet == null) {
                sheet = workbook.createSheet("sheet");
            }
            if (ArrayUtils.isNotEmpty(header)) {
                createExcelTitle(sheet, header);
            }
            if (CollectionUtils.isEmpty(objects)) {
                logger.info("导出文件数据为空 终止操作");
                return;
            }
            createExcelContent(sheet, objects, rowBuilder);
        } catch (Exception e) {
            logger.error("导出Excel文件异常", e);
            throw new BizException("导出Excel文件异常,请联系系统管理员处理");
        }
    }

    public static void createExcelTitle(Sheet sheet, String[] header) {
        createExcelRow(sheet, header, 0);
    }

    public static <T> void createExcelContent(Sheet sheet, Collection<T> objects, RowBuilder<T> rowBuilder) {
        if (CollectionUtils.isEmpty(objects)) {
            return;
        }
        int index = sheet.getLastRowNum() + 1;
        for (T t : objects) {
            createExcelRow(sheet, rowBuilder.build(t), index++);
        }
    }

    private static void createExcelRow(Sheet sheet, String[] rowData, int index) {
        Row row = sheet.createRow(index);
        for (int i = 0, len = rowData.length; i < len; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rowData[i]);
        }
    }

    public static void createExcelRow(Sheet sheet, String[] rowData) {
        createExcelRow(sheet, rowData, sheet.getLastRowNum() + 1);
    }

    public static void createExcelRow(Sheet sheet, Collection<String[]> rowDatas) {
        for (String[] row : rowDatas) {
            createExcelRow(sheet, row, sheet.getLastRowNum() + 1);
        }
    }

    public static <T> void exportExcelFile(String fileName, String[] header, RowBuilder<T> rowBuilder,
                                           SelectIterator<T> iterator, HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;fileName=" + new String(fileName.getBytes("GBK"), "iso-8859-1") + FILE_SUFFIX);

		try (SXSSFWorkbook workbook = new SXSSFWorkbook(1000)) {
			Sheet sheet = workbook.createSheet("sheet");
			if (ArrayUtils.isNotEmpty(header)) {
				createExcelTitle(sheet, header);
			}
			while (iterator.hasNext()) {
				createExcelContent(sheet, iterator.next(), rowBuilder);
			}
			workbook.write(response.getOutputStream());
			workbook.dispose();
		} catch (BizException e) {
			throw e;
		}
	}

	public static <T> InputStream convertToExcelFileData(Collection<T> objects, ExcelFileBuilder<T> builder) {
		return convertToExcelFileData(objects, builder.getHeader(), builder.getRowBuilder());
	}

    public static <T> InputStream convertToExcelFileDataNew(Collection<T> objects, String[] header, RowBuilder<T> rowBuilder) {
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet("sheet");
            if (ArrayUtils.isNotEmpty(header)) {
                createExcelTitle(sheet, header);
            }
            createExcelContent(sheet, objects, rowBuilder);
            workbook.write(bos);
            workbook.close();
            bos.close();
            //workbook.dispose();
            return new ByteArrayInputStream(bos.toByteArray());
        } catch (IOException e) {
            logger.error("生成vos文件异常", e);
            throw new BizException("生成校验文件异常，请联系系统管理员");
        }
    }

	public static <T> InputStream convertToExcelFileData(Collection<T> objects, String[] header, RowBuilder<T> rowBuilder) {
		try {
			SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			Sheet sheet = workbook.createSheet("sheet");
			if (ArrayUtils.isNotEmpty(header)) {
				createExcelTitle(sheet, header);
			}
			createExcelContent(sheet, objects, rowBuilder);
			workbook.write(bos);
			workbook.dispose();
			return new ByteArrayInputStream(bos.toByteArray());
		} catch (IOException e) {
			logger.error("生成vos文件异常", e);
			throw new BizException("生成校验文件异常，请联系系统管理员");
		}
	}

	/**
	 * 上传excel文件，如文件后缀不是“.xlsx”，抛出 {@link BizException}
	 */
	public static File checkAndUpload(MultipartFile file) {
		if (file == null) {
			throw new BizException("导入文件为空");
		}
		if (!file.getOriginalFilename().toLowerCase().endsWith(ExcelUtils.FILE_SUFFIX)) {
			throw new BizException(String.format("暂只支持后缀为%s的EXCEL格式文件，请检查。", ExcelUtils.FILE_SUFFIX));
		}
		return uploadFile(file);
	}

	private static File mkUploadDir() {
        String path ="/apps/dat";
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            try {
                uploadDir.mkdirs();
            } catch (Exception e) {
                logger.error("创建文件目录失败", e);
                throw new BizException("创建文件目录失败");
            }
        }
        return uploadDir;
	}

	public static File copyInputStreamToFile(InputStream is) {
		File uploadDir = mkUploadDir();
		String newFileName = UUID.randomUUID() + FILE_SUFFIX;// 上传后的文件名

		File destination = new File(uploadDir, newFileName);
		try {
			FileUtils.copyInputStreamToFile(is, destination);
		} catch (IOException e) {
            logger.error("上传文件异常", e);
            throw new BizException("上传文件异常",e);
		}
		return destination;
	}

    public static File uploadFile(MultipartFile excelFile) {

    	File uploadDir = mkUploadDir();

        String newFileName = UUID.randomUUID() + FILE_SUFFIX;// 上传后的文件名
        if (excelFile.isEmpty()) {
            throw new BizException("上传文件为空");
        }
        logger.info("上传文件到临时目录开始. 源文件名={}, 临时文件名={}", excelFile.getOriginalFilename(), newFileName);
        File uploadFile = new File(uploadDir, newFileName);
        try {
            excelFile.transferTo(uploadFile);
        } catch (Exception e) {
            logger.error("上传文件异常. 源文件名={}, 临时文件名={}", excelFile.getOriginalFilename(), newFileName, e);
            throw new BizException("上传文件异常",e);
        }
        return uploadFile;
    }

    public static <T> List<T> validImportExcelData(File xlsxFile, int rowLimit, RowValidFunction<T> rowValidAction) {
        return validImportExcelData(xlsxFile, rowLimit, rowValidAction, null);
    }


    public static <T> List<T> validImportExcelDataWithHeadNum(File xlsxFile, int rowLimit, RowValidFunction<T> rowValidAction,Integer headNum) {
        return validImportExcelDataWithCheckHead(xlsxFile, rowLimit, rowValidAction, null,headNum);
    }

    public static <T> List<T> validImportExcelData(File xlsxFile, int rowLimit, RowValidFunction<T> rowValidAction, String extErrLog) {
    	return validImportExcelDataWithCheckHead(xlsxFile,rowLimit,rowValidAction,extErrLog,null);
    }

    private static <T> List<T> validImportExcelDataWithCheckHead(File xlsxFile, int rowLimit, RowValidFunction<T> rowValidAction, String extErrLog,Integer headNum) {
        List<T> errMsgList = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(xlsxFile)) {
            int dataRow = 1;
            int row = 1;
            ExcelUtils.ExcelReader reader = new ExcelUtils.ExcelReader(inputStream, 0);
            if (reader.hasNext()) { //忽略文件头
                String[] header = reader.next();

                if(headNum!=null){
                    Assert.isTrue(header != null && header.length == headNum, "文件表头字段错误，请下载最新导入模版再行操作。");
                }

            }
            while (reader.hasNext()) {
                String[] data = reader.next();
                row++;
                if (isImportLineEmpty(data)) { // 跳过空行
                    continue;
                }
                if (dataRow > rowLimit) {
                    throw new BizException("行数超过限制的最大数量" + rowLimit + "，请检查。");
                }
                T errMsg = rowValidAction.apply(row, data);
                if (errMsg != null) {
                    errMsgList.add(errMsg);
                    if (errMsgList.size() >= 1000) { // 错误超过1000，直接返回
                        break;
                    }
                }
                dataRow++;
            }
            if (dataRow <= 1) {
                throw new BizException("导入文件无有效数据，请检查。");
            }
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            logger.error("导入文件异常. tempFile={}", xlsxFile.getName() + ". "
                    + (StringUtils.isBlank(extErrLog) ? StringUtils.EMPTY : extErrLog), e);
            throw new BizException("导入文件异常.");
        }
        return errMsgList;
    }

	/**
	 * 跳过表头，处理文件体
	 */
	public static int processFileBody(File tempFile, Processor processor, int pageSize) {
		int rows = 0;
		try (FileInputStream inputStream = new FileInputStream(tempFile)) {
			ExcelUtils.ExcelReader reader = new ExcelUtils.ExcelReader(inputStream, 0);
			if (reader.hasNext()) { //忽略文件头
				reader.next();
			}
			while (reader.hasNext()) {
				List<String[]> lineList = readPage(reader, pageSize);
				if (!CollectionUtils.isEmpty(lineList)) {
					processor.process(lineList);
					rows += lineList.size();
				}
			}
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			logger.error("tempFile=" + tempFile.getName(), e);
			throw new BizException("导入文件异常.");
		}
		return rows;
	}

	/**
	 * 跳过表头，获取文件体行数
	 */
	public static int getFileLineNum(File tempFile) {
		try (FileInputStream inputStream = new FileInputStream(tempFile)) {
		    int lineNum = 0;
			ExcelUtils.ExcelReader reader = new ExcelUtils.ExcelReader(inputStream, 0);
			if (reader.hasNext()) { //忽略文件头
				reader.next();
			}
			while (reader.hasNext()) {
                String[] line = reader.next();
                if (!isImportLineEmpty(line)) {
                    lineNum++;
                }
			}
			return lineNum;
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			logger.error("tempFile=" + tempFile.getName(), e);
			throw new BizException("导入文件异常.");
		}
	}

	public static void flushWorkbookToFile(SXSSFWorkbook workbook, File file) throws IOException {
		try (OutputStream out = new FileOutputStream(file)) {
			workbook.write(out);
			workbook.dispose();
			workbook.close();
		}
	}

	private static List<String[]> readPage(ExcelUtils.ExcelReader reader, int pageSize) {
		List<String[]> list = new ArrayList<>(pageSize);
		while (reader.hasNext()) {
			if (list.size() >= pageSize) {
				break;
			}
			String[] line = reader.next();
			if (!isImportLineEmpty(line)) {
				list.add(line);
			}
		}
		return list;
	}

	public interface Processor {
		void process(List<String[]> lineList);
	}

	public interface TaskProcessor<T> {
		List<T> process(List<String[]> lineList);
	}

    public interface RowValidFunction<E> {
        E apply(int rowNum, String[] rowData);
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ExcelFileBuilder<T> {
        private String[] header;
        private RowBuilder<T> rowBuilder;
    }
    public static class ExcelReader implements Iterator<String[]> {
        private Iterator<Row> rowIterator;
        private final int totalRow;
        private int cursor;

        public ExcelReader(InputStream inputStream, int sheetIndex) {
            Integer rowCacheSize = 1000;
            Workbook wk = StreamingReader.builder()
                    //缓存到内存中的行数，默认是10
                    .rowCacheSize(rowCacheSize)
                    //读取资源时，缓存到内存的字节大小，默认是1024
                    .bufferSize(4096)
                    //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
                    .open(inputStream);
            Sheet sheet = wk.getSheetAt(sheetIndex);
            this.rowIterator = sheet.rowIterator();
            this.totalRow = sheet.getLastRowNum();
        }

        public ExcelReader(InputStream inputStream, int sheetIndex, boolean ignoreHead) {
            this(inputStream, sheetIndex);
            if (ignoreHead && hasNext()) {
                next();
            }
        }
        @Override
        public boolean hasNext() {
            return null != rowIterator && rowIterator.hasNext();
        }
        @Override
        public String[] next() {
            if (!hasNext()) {
                return null;
            }
            cursor++;
            StreamingRow streamingRow = (StreamingRow) rowIterator.next();
            short lastCellNum = streamingRow.getLastCellNum();
			if (lastCellNum < 0) {
				return new String[]{};
			}
            String[] cells = new String[lastCellNum];

            for (int idx = 0; idx < lastCellNum; idx++) {
                Cell cell = streamingRow.getCell(idx);
                if (cell == null) {//可能会为null
                    continue;
                }
                cells[idx] = StringUtils.trim(cell.getStringCellValue());
            }
            return cells;
        }

        public List<String[]> next(int pageSize, boolean continueEmptLine) {
            List<String[]> page = new ArrayList<>(pageSize);
            while (hasNext()) {
                String[] row = next();
                if (continueEmptLine && isEmptyRow(row)) {
                    continue;
                }
                page.add(row);
                if (page.size() == pageSize) { //满一页或到达最后行
                    break;
                }
            }
            return page;
        }

        public boolean isEmptyRow(String[] row) {
            if (row == null || row.length == 0) {
                return true;
            }
            for (String str : row) {
                if (StringUtils.isNotBlank(str)) {
                    return false;
                }
            }
            return true;
        }

        public int getTotalRow() {
            return totalRow;
        }

        public int getCursor() {
            return cursor;
        }
    }

    public interface RowBuilder<T> {
        String[] build(T object);
    }

    public static <T> T mapper(String[] line, Class<T> c) {
		T obj = null;
		try {
			obj = c.newInstance();
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields) {
				boolean exists = field.isAnnotationPresent(ExcelMapper.class);
				if (!exists) {
					continue;
				}

				ExcelMapper column = field.getAnnotation(ExcelMapper.class);
				int rowNum = column.rowNum();
				if (line.length < rowNum + 1 || StringUtils.isBlank(line[rowNum])) {
					continue;
				}

				String fieldType = field.getType().getSimpleName();
				field.setAccessible(true);
				if ("String".equals(fieldType)) {
					field.set(obj, line[rowNum]);
				} else if ("Date".equals(fieldType)) {
					field.set(obj, DateUtils.toDate(line[rowNum], column.dateFormat()));
				} else if ("Long".equals(fieldType)) {
					field.set(obj, Long.valueOf(line[rowNum]));
				} else if ("BigDecimal".equals(fieldType)) {
					field.set(obj, new BigDecimal(line[rowNum]));
				} else if ("Integer".equals(fieldType)) {
					field.set(obj, Integer.valueOf(line[rowNum]));
				} else if ("Byte".equals(fieldType)) {
					field.set(obj, Byte.valueOf(line[rowNum]));
				} else {
					throw new BizException("不支持的类型");
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new BizException(e);
		}
		return obj;
	}

	public static List<String[]> getDistinctList(File xlsxFile) throws Exception {
		Set<String[]> dataSet = new HashSet<String[]>();
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(xlsxFile);
			ExcelUtils.ExcelReader reader = new ExcelUtils.ExcelReader(inputStream, 0);
			if (reader.hasNext()) {
				String[] header = reader.next();
				if (header == null || header.length == 0) {
					throw new Exception("table header is empty");
				}
			}
			while (reader.hasNext()) {
				String[] data = reader.next();
				dataSet.add(data);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return new ArrayList<String[]>(dataSet);
	}

	/**
	 * 对行数据进行预处理，支持设置行选填数据，避免最后一行选填为空时excel列数不对导致导入失败
	 */
	public static String[] prepareLine(String[] source, int length) {
		if (source == null || source.length == 0 || source.length >= length) {
			return source;
		}
		String[] line = new String[length];
		for (int i = 0; i < length; i++) {
			if (i >= source.length) {
				continue;
			}
			line[i] = source[i];
		}
		return line;
	}

	public static ByteArrayInputStream convertWorkBook2Stream(Workbook workbook){
		try {
			//SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			workbook.close();
			bos.close();
			return new ByteArrayInputStream(bos.toByteArray());
		} catch (IOException e) {
			logger.error("生成vos文件异常", e);
			throw new BizException("生成校验文件异常，请联系系统管理员");
		}
	}

	/**
	 * @param line 数据行（一行）
	 * @param rowIndex 行遍历下标，从 0 开始
	 * @param colIndex 列遍历下标，从 0 开始
	 * @param clazz 需要返回的类型
	 * @param <T>
	 * @return
	 */
	public static <T> T transExcelDataStr(String[] line, int rowIndex, int colIndex, Class<T> clazz) throws Exception {
		return transExcelDataStr(line, rowIndex, colIndex, clazz, DateUtils.DefaultDateTimePattern);
	}

	/**
	 * @param line 数据行（一行）
	 * @param rowIndex 行遍历下标，从 0 开始
	 * @param colIndex 列遍历下标，从 0 开始
	 * @param clazz 需要返回的类型
	 * @param dateFormatPattern 对日期类型指定的转换格式
	 * @param <T>
	 * @return
	 */
	public static <T> T transExcelDataStr(String[] line, int rowIndex, int colIndex, Class<T> clazz, String dateFormatPattern) throws Exception {
		//Excel的数据行从第 2 行开始（第一行为表头）
		int roWFromId = 2;
		String errMsg = String.format("导入的文件第 %d 行第 %d 列单元格值-格式有误，错误值：%s", rowIndex + roWFromId, colIndex + 1, line[colIndex]);
		try {
			if (clazz.isAssignableFrom(String.class)) {
				return (T) line[colIndex];
			}

			if (clazz.isAssignableFrom(BigDecimal.class)) {
				return (T) new BigDecimal(line[colIndex]);
			}

			if (clazz.isAssignableFrom(Integer.class)) {
				return (T) Integer.valueOf(line[colIndex]);
			}

			if (clazz.isAssignableFrom(Byte.class)) {
				return (T) Byte.valueOf(line[colIndex]);
			}

			if (clazz.isAssignableFrom(Short.class)) {
				return (T) Short.valueOf(line[colIndex]);
			}

			if (clazz.isAssignableFrom(Long.class)) {
				return (T) Long.valueOf(line[colIndex]);
			}

			if (clazz.isAssignableFrom(Boolean.class)) {
				return (T) Boolean.valueOf(line[colIndex]);
			}

			if (clazz.isAssignableFrom(Double.class)) {
				return (T) Double.valueOf(line[colIndex]);
			}

			if (clazz.isAssignableFrom(Date.class)) {
				if (StringUtils.isBlank(line[colIndex])) {
					return null;
				}
				return (T) FastDateFormat.getInstance(dateFormatPattern).parse(line[colIndex]);
			}
			throw new IllegalArgumentException("传入的参数类型不支持转换," + String.format("对文件第 %d 行第 %d 列单元格值处理的调用方法有误，" +
					"不支持的参数类型：%s.class", rowIndex + roWFromId, colIndex + 1, clazz.getSimpleName()));
		} catch (ParseException e) {
			throw new BizException(errMsg, e);
		} catch (NumberFormatException e) {
			throw new BizException(errMsg, e);
		}

	}

    public static boolean isImportLineEmpty(String[] line) {
        if(ArrayUtils.isEmpty(line)) {
            return true;
        }
        for (String str : line) {
            if(StringUtils.isNotBlank(str)) {
                return false;
            }
        }
        return true;
    }

}
