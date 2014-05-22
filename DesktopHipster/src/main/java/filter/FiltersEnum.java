package filter;

/**
 * Enum of all filters. Add new filters here.
 * 
 * @author Edvard H��binette
 * @revised Lovisa J��berg
 * 
 */
public enum FiltersEnum {

	NOFILTERFILTER(new NoFilterFilter()),BWFILTER(new BlackWhiteFilter()), 
	SEPIAFILTER(new SepiaFilter()), OLDSTYLEFILTER(new OldStyleFilter());

	private IFilter filter;

	private FiltersEnum(IFilter filter) {
		this.filter = filter;
	}

	public IFilter getFilter() {
		return filter;
	}

	public String getName() {
		return name();
	}
}
