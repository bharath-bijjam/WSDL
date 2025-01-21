package com.example.bharath;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@SpringBootApplication
public class BharathApplication {

	public static void main(String[] args) {
		SpringApplication.run(BharathApplication.class, args);
	}

	@Bean
	public CommandLineRunner generateWsdlFile(@Qualifier("countries") DefaultWsdl11Definition wsdl11Definition) {
		return args -> {
			try {
				// Set up WSDL source and output
				Source wsdlSource = wsdl11Definition.getSource();
				File wsdlFile = new File("countries.wsdl");
				try (OutputStream outputStream = new FileOutputStream(wsdlFile)) {
					// Write WSDL to file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.transform(wsdlSource, new StreamResult(outputStream));
				}
				System.out.println("WSDL file saved: " + wsdlFile.getAbsolutePath());
			} catch (Exception e) {
				System.err.println("Error generating WSDL file: " + e.getMessage());
				e.printStackTrace();
			}
		};
	}

}
