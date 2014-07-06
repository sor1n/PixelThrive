package net.PixelThrive.Client;

import net.PixelThrive.Client.GUI.ErrorScreen;

public class PixelException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public enum ExceptionType
	{
		WORLDGEN, OVERLOAD, OUTOFWORLD, NOTENOUGHIDS, ENTITYCORRUPT, AUDIOINIT, OTHER;

		public static int getID(ExceptionType type)
		{
			for(int i = 0; i < values().length; i++) if(values()[i] == type) return i;
			return -1;
		}
	}

	/**
	 * prints out the correct error message.
	 * @param message
	 * @param type
	 */
	public PixelException(String message, ExceptionType type, String extra, Throwable cause)
	{
		super(message);
		String err = "";
		switch(type)
		{
		case WORLDGEN:
			err = "Something went wrong with world generation!";
			Main.consoleError(err);
			openScreen(type, message, extra, cause, err);
			break;
		case OVERLOAD:
			err = "Main component overloading!";
			Main.consoleError(err);
			openScreen(type, message, extra, cause, err);
			break;
		case OUTOFWORLD:
			err = "Out of world bounds!";
			Main.consoleError(err);
			openScreen(type, message, extra, cause, err);
			break;
		case NOTENOUGHIDS:
			err = message;
			Main.consoleError(err);
			openScreen(type, message, extra, cause, err);
			break;
		case ENTITYCORRUPT:
			err = "Something went wrong with entity spawning!";
			Main.consoleError(err);
			openScreen(type, message, extra, cause, err);
			break;
		case AUDIOINIT:
			err = "Some audio files aren't initialized properly!";
			Main.consoleError(err);
			openScreen(type, message, extra, cause, err);
			break;
		default:
		case OTHER:
			err = "Unknown cause!";
			openScreen(type, message, extra, cause, err);
			Main.consoleError(err);
			break;
		}
	}
	
	/**
	 * Generate a new error screen
	 * @param message e.getMessage()
	 * @param mess Info
	 */
	public void openScreen(ExceptionType et, String message, String mess, Throwable error, String errorStr)
	{
		String str;
		if(error == null || error.getLocalizedMessage() == null) str = "";
		else str = error.getLocalizedMessage();
		new ErrorScreen(et, message, mess, str, ExceptionType.getID(et), errorStr);
	}
}