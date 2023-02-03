package com.wqzeng.springbtgradle;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 数据库常用实体对象生成器
 */
public class CodeGenerator {
	private static String entity = "entity";
	private static String mapper = "mapper";
	private static String service = "service";
	private static String serviceImpl = "service.impl";
	private static String controller = "controller";
	private static String outPath = System.getProperty("user.dir") + "/src/main/java";

	public static void main(String[] args) {
		new CodeGenerator().generatorFromDatabase();
	}

	private void generatorFromDatabase() {
		// user -> UserService, 设置成true: user -> IUserService
		generateByTables(false, "com.wqzeng.springbtgradle.model", "entity");
	}

	private void generateByTables(boolean startWithI, String packageName, String moduleName) {
		GlobalConfig config = new GlobalConfig();
//		String dbUrl = "jdbc:mysql://10.28.12.100:3307/kmcgc?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
		String dbUrl = "jdbc:mysql://localhost:3306/ims?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername("root").setPassword("zwq13610018797")
				.setDriverName("com.mysql.cj.jdbc.Driver");
		StrategyConfig strategy = new StrategyConfig().setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
				.setColumnNaming(NamingStrategy.underline_to_camel) // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
				// .setSuperEntityClass("com.baomidou.ant.common.BaseEntity")//
				// 自定义继承的Entity类全称，带包名
				.setEntityLombokModel(true) // 【实体】是否为lombok模型（默认 false）
				.setEntityColumnConstant(true)// 生成字段常量
//				.setVersionFieldName("id")
//				.setSuperEntityClass("com.isoftstone.kmcgcsystem.modules.common.entity.TableBaseEntity")//继承实体类
//				.setSuperEntityColumns("id")//继承实体属性
				.setRestControllerStyle(false) // 生成 @RestController 控制器
				// .setSuperControllerClass("com.baomidou.ant.common.BaseController") //
				// 自定义继承的Controller类全称，带包名
				// .setSuperEntityColumns("id") // 需要包含的表名，允许正则表达式,自定义基础的Entity类，公共字段
				.setControllerMappingHyphenStyle(true) // 驼峰转连字符
				.setEntityBooleanColumnRemoveIsPrefix(true)
				.setInclude("")
//				.setExclude("")
				.setTablePrefix("");
		config.setActiveRecord(true).setAuthor("autoGenerator")
				// 生成文件输出目录
				.setOutputDir(outPath)
				// XML ResultMap
				.setBaseResultMap(true)
				// XML columList;
				.setBaseColumnList(true).setFileOverride(true).setEnableCache(false).setIdType(IdType.ID_WORKER_STR)
				.setDateType(DateType.ONLY_DATE);
		if (!startWithI) {
			config.setServiceName("%sService");
		}

		PackageConfig pcf = initPackage(packageName, moduleName);

		TemplateConfig tc = new TemplateConfig();
		tc.setEntity("entity.java");
		new AutoGenerator().setGlobalConfig(config).setDataSource(dataSourceConfig).setStrategy(strategy)
				.setPackageInfo(pcf).setTemplate(tc).execute();
	}

	private PackageConfig initPackage(String packageName, String moduleName) {
		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setParent(packageName);
		packageConfig.setService(service);
		packageConfig.setServiceImpl(serviceImpl);
		packageConfig.setController(controller);
		packageConfig.setEntity(entity);
		packageConfig.setMapper(mapper);
		packageConfig.setXml(String.format("%s.xml", mapper));
		packageConfig.setModuleName(moduleName);
		return packageConfig;
	}

}
