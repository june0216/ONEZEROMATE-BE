package com.jiyun.recode.global.custom;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CustomMultipartFile implements MultipartFile {
	private byte[] input;

	public CustomMultipartFile(byte[] input) {
		this.input = input;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getOriginalFilename() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

		//previous methods
		@Override
		public boolean isEmpty() {
			return input == null || input.length == 0;
		}

		@Override
		public long getSize() {
			return input.length;
		}

		@Override
		public byte[] getBytes() throws IOException {
			return input;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(input);
		}

		@Override
		public void transferTo(File destination) throws IOException, IllegalStateException {
			try(FileOutputStream fos = new FileOutputStream(destination)) {
				fos.write(input);
			}
		}



}
