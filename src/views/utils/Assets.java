package views.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class Assets {
	public static final String ASSETS_PATH="/views/assets/";	// Assets path
	public static Color _gameLablesColor;
	private static Font _baseFont;

	public static Color getCurrentLevelForegroundColor() {
		return _gameLablesColor;
	}

	public static URL getResource(Class<?> classObj ,String assetName) {
		return classObj.getResource(ASSETS_PATH + classObj.getSimpleName() + "/" + assetName);
	}

	public static URL getResource(String assetName) {
		return URLClassLoader.class.getResource(ASSETS_PATH + assetName);
	}

	public static InputStream getResourceAsStream(String fullPath) {
		return URLClassLoader.class.getResourceAsStream(ASSETS_PATH + fullPath);
	}

	public static Font getBaseFont() {
		if (_baseFont == null) {
			try {
				_baseFont = Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/dosFont.ttf"));

				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    ge.registerFont(_baseFont);
			} catch (IOException e) {
				_baseFont = new Font("Arial", Font.PLAIN, 18);
			} catch (FontFormatException e) {
				_baseFont = new Font("Arial", Font.PLAIN, 18);
			}
		}
		
		return _baseFont;
	}
}
