package com.authentication.utils;
 

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreateObjectID {
	private final int numberOfBitsInAHalfByte = 4;
	private final int halfByte = 0x0F;
	private final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private Date date = new Date();
	private LocalDateTime localDateTime;
	private SimpleDateFormat formatter;

	public CreateObjectID() {
		formatter = new SimpleDateFormat("MMdd_hhmm");
	}

	public String lendIdToObjectId(String s) {
		StringBuffer objId = new StringBuffer("1");
		String[] splitString = s.split("_");

		int productDigit = 4;
		int dtDigit = 3;
		int runningDigit = 8;
		int campaignDigit = 8;

		String product = splitString[0];
		String dt = splitString[1];
		String running = splitString[2];
		String campaign = splitString.length < 4 ? "0" : splitString[3];

		// System.out.println(parse36ToHex(product, productDigit));
		// System.out.println(parseDateToHex(dt, dtDigit));
		// System.out.println(longToHex(Long.valueOf(running), runningDigit));
		// System.out.println(parse36ToHex(campaign, campaignDigit));
		objId.append(parse36ToHex(product, productDigit)).append(parseDateToHex(dt, dtDigit))
				.append(longToHex(Long.valueOf(running), runningDigit)).append(parse36ToHex(campaign, campaignDigit));

		return objId.toString();
	}

	protected int charToInt(char c) {
		return Character.getNumericValue(c);
	}

	protected String decToHex(int dec, int size) {
		StringBuilder hexBuilder = new StringBuilder(size);
		hexBuilder.setLength(size);
		for (int i = size - 1; i >= 0; --i) {
			int j = dec & halfByte;
			hexBuilder.setCharAt(i, hexDigits[j]);
			dec >>= numberOfBitsInAHalfByte;
		}
		return hexBuilder.toString();
	}

	protected String longToHex(long longNumber, int size) {
		String hexChars = String.format("%0" + size + "X", longNumber);
		return hexChars;
	}

	public String parse36ToHex(String input, int size) {
		long number = parse36ToLong(input);
		return longToHex(number, size);
	}

	public long parse36ToLong(String input) {
		int charInt;
		long sum = 0;
		for (int power = 0, digit = input.length() - 1; digit >= 0; power++, digit--) {
			charInt = charToInt(input.charAt(digit));
			sum += (Math.pow(36, power) * charInt);
		}
		return sum;
	}

	public long formatWithFirstDateOfYearDiff(String dateString) {
		LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyyMMdd"));
		StringBuilder builder = new StringBuilder();
		builder.append(localDate.getDayOfYear());
		builder.append(localDate.getYear() % 10);
		return Long.valueOf(builder.toString());
	}

	public String parseDateToHex(String input, int size) {
		long dateAfterFormat = formatWithFirstDateOfYearDiff(input);
		return longToHex(dateAfterFormat, size);
	}

	public String getTextId(String name) {
		if (name.length() == 0) {
			return "";
		}
		String textId = "";
		if (name.length() == 3) {
			textId = name;
		} else if (name.length() > 3) {
			textId = name.substring(0, 3);
		} else {
			textId = name;
			for (int i = 0; i < 3 - name.length(); i++) {
				textId += "" + i;
			}
		}
		localDateTime = LocalDateTime.now();
		return textId + "_" + localDateTime.getYear() + formatter.format(date);
	}
}
