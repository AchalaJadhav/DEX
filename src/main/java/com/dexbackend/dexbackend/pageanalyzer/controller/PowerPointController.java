//package com.dexbackend.dexbackend.pageanalyzer.controller;
//
//
//import org.apache.xmlbeans.XmlException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.dexbackend.dexbackend.authentication.jwt.jwtconfig.CustomJwt;
//import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticsearchService;
//import com.dexbackend.dexbackend.pageanalyzer.utilities.PowerPointService;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//
//import java.io.IOException;
//import java.text.MessageFormat;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@CrossOrigin(
//        origins = "http://localhost:4200",
//        allowedHeaders = "*"
//)
//public class PowerPointController {
//	
//	@Autowired
//    private  PowerPointService powerPointService;
//	@Autowired
//	private  ElasticsearchService elasticsearchService;
//
//	
//
//    
//}
//
