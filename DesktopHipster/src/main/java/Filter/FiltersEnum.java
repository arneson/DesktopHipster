package filter;

public enum FiltersEnum {

	BWFILTER (new BlackWhiteFilter());
	
	
	private IFilter filter;
	private FiltersEnum(IFilter filter){
		this.filter = filter;
	}
	
	public IFilter getFilter(){
		return filter;
	}
}
