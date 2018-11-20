package fi.haagahelia.course.morpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FileNames")
public class FileName {
	    @Id
	    @Field
	    private String id;
	    @Field
	    private String name;
	    
		public FileName() {
			
		}
	    
		public FileName(String id, String name) {
			this.id = id;
			this.name = name;
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
}
