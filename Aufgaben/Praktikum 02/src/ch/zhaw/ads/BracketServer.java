package ch.zhaw.ads;

public class BracketServer implements CommandExecutor {
	enum ParenthesisType {ROUND, SQUARE, CURLY, TRIANGULAR, COMMENT}

	static class Parenthesis {
		public ParenthesisType parenthesisType;
		public final boolean isOpen;

		public Parenthesis(ParenthesisType parenthesisType, boolean isOpen) {
			this.parenthesisType = parenthesisType;
			this.isOpen = isOpen;
		}
	}

	private boolean waitingForSlash;
	private boolean waitingForStar;

	/**
	 * execute -- nimmt eine Kommandozeile, tut irgendetwas Gescheites, und
	 * berichtet das Resultat.
	 *
	 * @param command Kommandozeile
	 * @return Resultat, unüblicherweise eine oder mehrere Zeilen.
	 */
	@Override
	public String execute(String command) {
		checkBrackets(command);
		return null;
	}

	public boolean checkBrackets(String command) {
		ListStack parenthesisStack = new ListStack();
		char[] charsInCommand = command.toCharArray();

		waitingForStar = false;
		waitingForSlash = false;

		int charIndex = 0;

		while (charIndex < charsInCommand.length) {
			char charToCheck = charsInCommand[charIndex];

			if (checkIfCharIsParenthesis(charToCheck)) {
				Parenthesis newParenthesis = identifyParenthesis(charToCheck);

				if(parenthesisStack.isEmpty()) {
					if(!newParenthesis.isOpen) return false;
					else parenthesisStack.push(newParenthesis);
				} else {
					Parenthesis lastParenthesis = (Parenthesis) parenthesisStack.peek();

					if(newParenthesis.isOpen)
						parenthesisStack.push(newParenthesis);
					else if(newParenthesis.parenthesisType == lastParenthesis.parenthesisType && lastParenthesis.isOpen)
						parenthesisStack.pop();
					else return false;
				}
			}

			charIndex++;

		}
		return parenthesisStack.isEmpty();
	}

	private boolean checkIfCharIsParenthesis(char charToCheck) {
		if      (charToCheck == '(')    return true;
		else if (charToCheck == ')')    return true;
		else if (charToCheck == '[')    return true;
		else if (charToCheck == ']')    return true;
		else if (charToCheck == '{')    return true;
		else if (charToCheck == '}')    return true;
		else if (charToCheck == '<')    return true;
		else if (charToCheck == '>')    return true;

		else if     (charToCheck == '/' && waitingForSlash) {
			waitingForSlash = false;
			return true;
		} else if   (charToCheck == '/') {
			waitingForStar = true;
			return false;
		} else if   (charToCheck == '*' && waitingForStar) {
			waitingForStar = false;
			return true;
		} else if   (charToCheck == '*') {
			waitingForSlash = true;
			return false;
		}

		else  return false;
	}

	private Parenthesis identifyParenthesis(char parenthesisToIdentify) {
		if      (parenthesisToIdentify == '(')  return new Parenthesis(ParenthesisType.ROUND,       true);
		else if (parenthesisToIdentify == ')')  return new Parenthesis(ParenthesisType.ROUND,       false);
		else if (parenthesisToIdentify == '[')  return new Parenthesis(ParenthesisType.SQUARE,      true);
		else if (parenthesisToIdentify == ']')  return new Parenthesis(ParenthesisType.SQUARE,      false);
		else if (parenthesisToIdentify == '{')  return new Parenthesis(ParenthesisType.CURLY,       true);
		else if (parenthesisToIdentify == '}')  return new Parenthesis(ParenthesisType.CURLY,       false);
		else if (parenthesisToIdentify == '<')  return new Parenthesis(ParenthesisType.TRIANGULAR,  true);
		else if (parenthesisToIdentify == '>')  return new Parenthesis(ParenthesisType.TRIANGULAR,  false);
		else if (parenthesisToIdentify == '*')  return new Parenthesis(ParenthesisType.COMMENT,     true);
		else                                    return new Parenthesis(ParenthesisType.COMMENT,     false);
	}
}

