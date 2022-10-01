package com.example.filexplorer;

import com.example.filexplorer.service.FileRegisterService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	final FileRegisterService fileRegisterService;

	public WebSocketConfig(FileRegisterService fileRegisterService) {
		this.fileRegisterService = fileRegisterService;
	}

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new FileExplorerSocketHandler(), "/explorer");
		registry.addHandler(new FileScanSocketHandler(fileRegisterService), "/scan");
	}

}