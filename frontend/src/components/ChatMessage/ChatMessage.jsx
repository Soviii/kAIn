import React from "react";
import "./ChatMessage.css" 

const ChatMessage = ({ sender, content }) => {
  const isUser = sender === "user";
  return (
    <div className="messageDiv">
      <div className={`message-bubble ${isUser === true ? "user" : "ai"}`}>
        {content}
      </div>
    </div>
  );
}

export default ChatMessage;
