package filter;


/**
 * Enum of all filters. Add new filters here.
 * 
 * @author Edvard Hübinette
 * @revised Lovisa Jäberg
 */
public enum FiltersEnum {

	BWFILTER 		(new BlackWhiteFilter()),
	SEPIAFILTER 	(new SepiaFilter()),
	OLDSTYLEFILTER	(new OldStyleFilter());
	
	private IFilter filter;
	private FiltersEnum(IFilter filter){
		this.filter = filter;
	}
	
	public IFilter getFilter(){
		return filter;
	}
	
	public String getName(){
		return name();
	}
}
