package com.example.filexplorer;

import com.example.filexplorer.dto.FilePathRequestDto;
import com.example.filexplorer.dto.FilePathResponseDto;
import com.example.filexplorer.dto.ServerResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FileScanSocketHandler extends TextWebSocketHandler {


    final Gson gson = new Gson();
    HashMap<String, WebSocketSession> sessions = new HashMap<>();

    public FileScanSocketHandler() {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
    }

    private void broadcast(String message) {

        for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
            try {
                final WebSocketSession webSocketSession = entry.getValue();

                webSocketSession.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendJson(WebSocketSession session, Object json) {
        try {
            session.sendMessage(new TextMessage(gson.toJson(json)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<FilePathResponseDto> listDirectory(FilePathRequestDto requestDto) {
        File targetDir = new File(requestDto.getPath());
        if (!(targetDir.exists() || targetDir.isDirectory())) {
            return new ArrayList<>();
        }
        return FileUtils.getFiles(targetDir).stream().map(file -> {
            FilePathResponseDto responseDto = new FilePathResponseDto();
            responseDto.setDirectory(file.isDirectory());
            responseDto.setName(file.getName());
            responseDto.setPath(file.getAbsolutePath());
            responseDto.setLenght(file.length());
            return responseDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        String receivedJson = message.getPayload();
        try {
            JsonObject commandElement = gson.fromJson(receivedJson, JsonObject.class);
            if (commandElement.isJsonNull() || !commandElement.isJsonObject()) {
                sendJson(session, new ServerResponseDto(false, "Command not found", null));
                return;
            }
            if (!commandElement.has("command")) {
                sendJson(session, new ServerResponseDto(false, "Command not found", null));
                return;
            }
            if ("list-dir".equals(commandElement.get("command").getAsString()) && !commandElement.get("path").isJsonNull()) {
                ServerResponseDto responseDto = new ServerResponseDto(
                        true,
                        "ok",
                        listDirectory(new FilePathRequestDto(commandElement.get("path").getAsString())),
                        "list-dir"
                );
                sendJson(session, responseDto);
                return;
            }
        } catch (JsonSyntaxException je) {
            je.printStackTrace();
            sendJson(session, new ServerResponseDto(false, je.getMessage(), null));
            return;
        }
        sendJson(session, new ServerResponseDto(false, "Command not found", null));
        //broadcast(receivedJson);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        sessions.put(session.getId(), session);

    }

}