package ch.zhaw.ads;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WellformedXmlServer implements CommandExecutor{

	private ListStack tagStack = new ListStack();

	@Override
	public String execute(String command) throws Exception {
		return null;
	}

	public boolean checkWellformed (String command) {
		Pattern pattern = Pattern.compile("</?[a-z=\" ]*>");
		Matcher matcher = pattern.matcher(command);

		while (matcher.find()){
			String[] afterOpeningTag = command.split("<", 2)[1].split(">", 2);
			command = afterOpeningTag[1];

			if(afterOpeningTag[0].startsWith("/")){
				if(!tagStack.isEmpty() && tagStack.peek().equals(afterOpeningTag[0].substring(1,2))){
					tagStack.pop();
				} else{
					tagStack.removeAll();
					return false;
				}
			} else{
				tagStack.push(afterOpeningTag[0].substring(0,1));
			}

		}
		if(tagStack.isEmpty()){
			return true;
		} else{
			tagStack.removeAll();
			return false;
		}
	}
}
