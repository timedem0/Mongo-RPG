package fi.haagahelia.course.morpg.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Files")
public class FileUpload {
    @Id
    @Field
    private String id;
    @Field
    private String name;
    @Field
    private String type;
    @Field
    private Binary file;
    
	public FileUpload() {

	}    
    
	public FileUpload(String name, String type, Binary file) {
		this.name = name;
		this.type = type;
		this.file = file;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Binary getFile() {
		return file;
	}

	public void setFile(Binary file) {
		this.file = file;
	}
}
