package com.garden.mybatis.generator;

import com.garden.mybatis.generator.freemarker.GenerateFile;

public class Generator {
	
	public static void execute() {
		GenerateFile.getInstance().generate();
	}

	public static void main(String[] args) {
		execute();
	}

}
