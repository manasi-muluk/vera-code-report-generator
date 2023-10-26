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
                myList.addAll(entities.stream()
                        .map(entity -> {
                            List<MyEntitiesRecordsInfo> reasons = entity.getRecords().stream()
                                    .flatMap(record -> record.getAttributes().stream())
                                    .filter(attrInfo -> "MY_REASON_DESCRIPTION".equalsIgnoreCase(attrInfo.getKey()))
                                    .map(attrInfo -> MyEntitiesRecordsInfo.builder()
                                            .key(attrInfo.getKey())
                                            .value(attrInfo.getValue())
                                            .build())
                                    .collect(Collectors.toList());

                            return My.builder().entities(reasons).build();
                        })
                        .collect(Collectors.toList());
            });
        });
        return myList;
    }
}
