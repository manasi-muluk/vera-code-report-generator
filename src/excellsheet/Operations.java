package excellsheet;

public interface Operations {
	public void readFromInitialExcel();

	public void writeExtractedDataToNewExcel();

	public void countBasedOnCategory();

	public void writeCountToExcel();
	
}

return Optional.ofNullable(responseWrapperDto)
                .map(data -> data.getData())
                .map(attr -> {
                    final MyAttribute myAttribute = this.objectMapper.convertValue(attr.getAttributes(), MyAttribute.class);
                    return Optional.ofNullable(myAttribute.getEntities())
                            .orElse(List.of())
                            .stream()
                            .flatMap(entities -> entities.getRecords().stream())
                            .map(record -> {
                                Optional<MyEntitiesRecordsAttributes> myEntitiesAttributes = record.getAttributes()
                                        .stream()
                                        .filter(vc -> "MY_REASON_DESCRIPTION".equalsIgnoreCase(vc.getKey()))
                                        .findFirst();

                                return myEntitiesAttributes.map(attr -> {
                                    MyEntitiesRecordsInfo info = new MyEntitiesRecordsInfo(attr.getKey(), attr.getValue());
                                    return new My(info);
                                }).orElse(null);
                            })
                            .filter(info -> info != null)
                            .collect(Collectors.toList());
                })
                .orElse(null);
