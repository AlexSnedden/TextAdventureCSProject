public class State {
    private String id;
    private String display;
    private AgentUpdates agentUpdates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public AgentUpdates getAgentUpdates() {
        return agentUpdates;
    }

    public void setAgentUpdates(AgentUpdates agentUpdates) {
        this.agentUpdates = agentUpdates;
    }

}
