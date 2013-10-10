package dataService;

import java.sql.Connection;

import relationalMapping.AssistPlayerRM;
import relationalMapping.AssistRM;
import relationalMapping.AssistShotRM;

public class AssistDataService extends DataService implements IAssistDataService
{

	public AssistDataService(Connection conn) 
	{
		super(conn);
	}

	@Override
	public void writeAssist(AssistRM assist) 
	{
		//TODO
	}

	@Override
	public void writeAssistPlayer(AssistPlayerRM assistPlayer) 
	{
		//TODO
	}

	@Override
	public void writeAssistShot(AssistShotRM assistShot) 
	{
		//TODO
	}

}
