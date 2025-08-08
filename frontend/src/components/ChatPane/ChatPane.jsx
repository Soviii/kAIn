import {useState, useEffect, useRef } from 'react';
import { Form, FormControl, Button, InputGroup } from "react-bootstrap";
// import { Button } from "@/components/ui/button";
// import { Input } from "@/components/ui/input";
import ChatMessage from '../ChatMessage/ChatMessage';
import "./ChatPane.css";

const ChatPane = () => {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const messagesEndRef = useRef(null);

  const isInDevMode = () => {
    if (process.env.NODE_ENV === "development") {
      // Running in development mode
      console.log("Development mode");
    } else {
      // Running in production mode
      console.log("Production mode");
    }
  }

  const fetchMessages = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/openai/getchat?recipeId=1", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        console.log('no messages found');
        return;
      }

      const data = await response.json();
      setMessages(data["messages"]);
      isInDevMode();

    } catch (err) {
      console.error(err.message);
    }
  }


  const handleSubmitMessage = async (e) => {
    e.preventDefault();
    try {
      setMessages((prevMessages) => ([
        ...prevMessages,
        {"sender": "user", "msg": input.trim()}
      ]))
      setInput("");
      const response = await fetch("http://localhost:8080/api/openai/userchat", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          recipeId: 1,
          message: input.trim()
        })
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      setMessages((prevMessages) => ([
        ...prevMessages,
        {"sender": "ai", "msg": data["aiMsg"].trim()},
      ]))
      console.log(data);

    } catch (err) {
      console.error(err.message);
    }
  }
  useEffect(() => {
    fetchMessages();
  }, []);

  // scrolls to bottom anytime page loads or new convo is loaded
  useEffect(() => {
    console.log(messages);
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  // for rendering chat message bubbles
  const renderMessages = () => {
    if (messages.length === 0) return (<span className="noMsgsAvailSpan">No messages here</span>);
    return messages.map((message, i) => (
      <ChatMessage key={i} sender={message.sender} content={message.msg} />
    ))
  }


  return (
    <div className="chatPaneDiv">
      <h2>AI Chat</h2>
      <div className="chatMessagesDiv">
        {renderMessages()}
        <div ref={messagesEndRef} />
      </div>
        <Form onSubmit={handleSubmitMessage} className="mt-3">
          <InputGroup>
            <FormControl
              placeholder="Type a message..."
              value={input}
              onChange={(e) => setInput(e.target.value)}
            />
            <Button disabled={input.trim() === ""} className="sendBtn" type="submit">
              Send
            </Button>
          </InputGroup>
      </Form>
    </div>
  )
};

export default ChatPane;