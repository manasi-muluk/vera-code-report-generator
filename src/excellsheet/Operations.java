package excellsheet;

public interface Operations {
	public void readFromInitialExcel();

	public void writeExtractedDataToNewExcel();

	public void countBasedOnCategory();

	public void writeCountToExcel();
	
}

List<My> myList = new ArrayList<>();
        Optional.ofNullable(responseWrapperDto).map(data -> data.getData()).ifPresent(attr -> {
            MyAttribute myAttribute = this.objectMapper.convertValue(attr.getAttributes(), MyAttribute.class);
            Optional.ofNullable(myAttribute.getEntities()).ifPresent(entities -> {
                myList = entities.stream()
                        .map(entity -> {
                            List<MyEntitiesRecordsInfo> reasons = entity.getRecords().stream()
                                    .flatMap(record -> record.getAttributes().stream())
                                    .filter(attrInfo -> "MY_REASON_DESCRIPTION".equalsIgnoreCase(attrInfo.getKey()))
                                    .map(attrInfo -> MyEntitiesRecordsInfo.builder()
                                            .key(attrInfo.getKey())
                                            .value(attrInfo.getValue())
                                            .build())
                                    .collect(Collectors.toList());

                            // Assuming you have an existing list of MyEntities
                            List<MyEntities> existingEntities = entity.getRecords().stream()
                                    .map(record -> new MyEntities(record.getName(), null)) // You may need to adjust this part
                                    .collect(Collectors.toList());

                            return My.builder()
                                    .entities(existingEntities) // Add your existing entities here
                                    .build();
                        })
                        .collect(Collectors.toList());
            });
        });
        return myList;
