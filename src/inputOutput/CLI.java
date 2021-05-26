package inputOutput;

import java.util.ArrayList;

import inputOutput.Commands.Command;
import inputOutput.Commands.DefaultIO;

public class CLI {

	ArrayList<Command> commands;
	DefaultIO dio;
	Commands c;
	
	public CLI(DefaultIO dio) {
		this.dio=dio;
		c=new Commands(dio); 
		commands=new ArrayList<>();
		// example: commands.add(c.new ExampleCommand());
		// implement
		commands.add(c.new command1());
		commands.add(c.new command2());
		commands.add(c.new command3());
		commands.add(c.new command4());
		commands.add(c.new command5());
		commands.add(c.new command6());

	}
	
	public void start() {
		// implement

		int numOfCommand=0;
		String readNum;
		while(numOfCommand!=6)
		{
			dio.write("Welcome to the Anomaly Detection Server.\n"
					  + "Please choose an option:\n");
			for(int i=0;i<6;i++)
			{
				dio.write(this.commands.get(i).description);
			}
			
			readNum= dio.readText();
			numOfCommand = Integer.parseInt(readNum);
			this.commands.get(numOfCommand-1).execute();
		}
	 }
}
