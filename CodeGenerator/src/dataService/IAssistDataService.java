package dataService;

import relationalMapping.AssistPlayerRM;
import relationalMapping.AssistRM;
import relationalMapping.AssistShotRM;

public interface IAssistDataService 
{
	void writeAssist(AssistRM assist);
	void writeAssistPlayer(AssistPlayerRM assistPlayer);
	void writeAssistShot(AssistShotRM assistShot);
}
