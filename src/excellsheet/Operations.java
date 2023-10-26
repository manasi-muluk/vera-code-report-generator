package excellsheet;

public interface Operations {
	public void readFromInitialExcel();

	public void writeExtractedDataToNewExcel();

	public void countBasedOnCategory();

	public void writeCountToExcel();
	
}

 List<Reason> reasons = new ArrayList<>();

        Optional.ofNullable(responseWrapperDto)
                .map(data -> data.getData())
                .map(attr -> {
                    final MyAttribute myAttribute = this.objectMapper.convertValue(attr.getAttributes(), MyAttribute.class);
                    return Optional.ofNullable(myAttribute.getEntities())
                            .orElse(new ArrayList<>())
                            .stream()
                            .flatMap(entities -> entities.getRecords().stream())
                            .map(record -> {
                                Optional<MyEntitiesRecordsAttributes> myEntitiesAttributes = record.getAttributes()
                                        .stream()
                                        .filter(vc -> "MY_REASON_DESCRIPTION".equalsIgnoreCase(vc.getKey()))
                                        .findFirst();

                                return myEntitiesAttributes.map(attr -> new Reason(attr.getValue())).orElse(null);
                            })
                            .filter(reason -> reason != null)
                            .collect(Collectors.toList());
                })
                .ifPresent(reasons::addAll);

        return reasons;
