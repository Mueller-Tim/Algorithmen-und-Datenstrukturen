package ch.zhaw.ads;

import java.util.Objects;

public class BracketServer implements CommandExecutor{

	private ListStack equationStack = new ListStack();
	private ListStack bracketStack = new ListStack();
	@Override
	public String execute(String command){
		return null;
	}

	public boolean checkBrackets(String equation){
		for(char c : equation.toCharArray()){
			equationStack.push(String.valueOf(c));
		}
		String nextBracket = "";
		while(!equationStack.isEmpty()){
			nextBracket = getNextBracket();
			if(Objects.nonNull(nextBracket) && nextBracket.matches("[)}\\]]")){
				bracketStack.push(nextBracket);
			} else{
				if(!bracketStack.isEmpty() && isSameBracket(nextBracket)){
					bracketStack.pop();
				} else{
					equationStack.removeAll();
					bracketStack.removeAll();
					return false;
				}
			}
		}
		if (bracketStack.isEmpty()){
			return true;
		} else{
			bracketStack.removeAll();
			return false;
		}
	}

	private String getNextBracket(){
		while(Objects.nonNull(equationStack.peek())){
			if(equationStack.peek().toString().matches("[(){}\\[\\]]")){
				return equationStack.pop().toString();
			} else{
				equationStack.pop();
			}

		}
		return null;
	}

	private boolean isSameBracket(String firstBracket){
		String secondBracket = switch (firstBracket) {
			case "(" -> ")";
			case "[" -> "]";
			case "{" -> "}";
			default -> "";
		};
		return bracketStack.peek().equals(secondBracket);
	}
}
