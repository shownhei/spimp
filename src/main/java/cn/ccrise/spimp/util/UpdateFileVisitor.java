package cn.ccrise.spimp.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * 更新文件匹配。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class UpdateFileVisitor extends SimpleFileVisitor<Path> {
	private PathMatcher matcher = null;
	private ArrayList<Path> list = new ArrayList<>();

	public UpdateFileVisitor(String pattern) {
		super();
		matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}

	public ArrayList<Path> getList() {
		return list;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		Path name = file.getFileName();
		if (matcher.matches(name)) {
			list.add(file);
		}
		return FileVisitResult.CONTINUE;
	}
}
