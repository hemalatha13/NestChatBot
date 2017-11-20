package example;
        
     public class RequestClass {
        Result result;
        OriginalRequest originalRequest;
        
        public Result getResult(){return result;}
        public void setResult(Result result) {this.result = result;  }
        
        public OriginalRequest getOriginalRequest(){return originalRequest;}
        public void setOriginalRequest(OriginalRequest originalRequest){this.originalRequest=originalRequest;}
        
        public RequestClass( Result result, OriginalRequest originalRequest) {
            this.result = result;
            this.originalRequest=originalRequest;
        }
        public RequestClass() {}
    }
     
     class Result {
    	public Parameters parameters;
    	public Metadata metadata;
    	
    	public Parameters getParameters(){return parameters; }
    	public void setParameters(Parameters parameters){this.parameters=parameters;}
    	
    	public Metadata getMetadata(){return metadata;}
    	public void setMetadata(Metadata metadata){this.metadata=metadata;}
    	
    	
    	 public Result(Parameters parameters){
     		this.parameters=parameters;
    	 }
    	 public Result(){}
    	 
     }
     
     class Parameters {
    	 public String  temperature;
    	 public String thermostats;
    	 public String readparameter;
    	 public String mode;
    	 public String scaleValue;
    	 public String pin;
    	 
    	 public String getPin(){return pin;}
    	 public void setPin(String pin){this.pin=pin;}

    	 public String getScaleValue(){return scaleValue;}
    	 public void setScaleValue(String scaleValue){this.scaleValue = scaleValue;}
    	 
    	 public String getTemperature(){return temperature;}
    	 public void setTemperature(String temperature){this.temperature = temperature;}
    	
    	 public String getThermostats(){return thermostats;}
    	 public void setThermostats(String thermostats){this.thermostats = thermostats;}
    	 
    	 public String getReadpaarameter(){return readparameter;}
    	 public void setReadparameter(String readparameter){this.readparameter = readparameter;}
    	 
    	 public String getMode(){return mode;}
    	 public void setMode(String mode){this.mode = mode;}
    	 
    	 
    	 public Parameters(String temperature, String thermostats, String readparameter, String mode, String scaleValue,String pin) {
    		 this.temperature = temperature; 
    		 this.thermostats = thermostats;
    		 this.readparameter = readparameter;
    		 this.mode = mode;
    		 this.scaleValue=scaleValue;
    		 this.pin=pin;
    	 }
    	 public Parameters(){}
     }
     
     class Metadata{
    	 public String intentName;
    	 
    	 public void setIntentName(String intentName){this.intentName=intentName;}
    	 public String getIntentName(){return intentName;}
    	 
    	 public Metadata(String intentName){ this.intentName=intentName;}
    	 public Metadata(){}
     }
     
     
     class OriginalRequest{
    	 public Data data;
    	 
    	 public void setData(Data data){this.data=data;}
    	 public Data getData(){return data;}
    	 
    	 public OriginalRequest(Data data){this.data=data;}
    	 public OriginalRequest(){}
     }
     
     class Data{
    	 public Sender sender;
    
    	 public void setSender(Sender sender){this.sender=sender;}
    	 public Sender getSender(){return sender;}
    	 
    	 public Data(Sender sender){this.sender=sender;}
    	 public Data(){}
     }
     
    class Sender{
    	public String id;
    
    	public void setId(String id){this.id=id;}
    	public String getId(){return id;}
    	
    	public Sender(String id){this.id=id;}
    	public Sender(){}
    } 
     