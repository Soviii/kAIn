import {useState, useEffect, useRef } from 'react';
import { Form, FormControl, Button, InputGroup } from "react-bootstrap";
import ChatMessage from '../ChatMessage/ChatMessage';
import "./ChatPane.css";

const ChatPane = ({ recipeId }) => {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const messagesEndRef = useRef(null);

  // fetches all messages for highlighted recipe
  // TODO: will have to update recipeId query parameter to be dynamic with highlighted recipe
  const fetchMessages = async () => {
    try {
      const response = await fetch("http://localhost:8080/openai/getchat", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "recipeId": recipeId
        },
        credentials: "include"
      });

      if (!response.ok) {
        console.log('no messages found');
        return;
      }

      const data = await response.json();
      setMessages(data["messages"]);
    } catch (err) {
      console.error(err.message);
    }
  }

  // handler for submitting user message to OpenAI GPT
  // returns response from OpenAI GPT
  const handleSubmitMessage = async (e) => {
    e.preventDefault();
    try {
      setMessages((prevMessages) => ([
        ...prevMessages,
        {"sender": "user", "msg": input.trim()}
      ]))
      setInput("");
      const response = await fetch("http://localhost:8080/openai/userchat", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          "recipeId": recipeId,
          "message": input.trim()
        }),
        credentials: "include"
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      setMessages((prevMessages) => ([
        ...prevMessages,
        {"sender": "ai", "msg": data["aiMsg"].trim()},
      ]))

    } catch (err) {
      console.error(err.message);
    }
  }

  // fetches messages for specific recipe (TODO: will have to update in the future...)
  useEffect(() => {
    fetchMessages();
  }, []);

  // scrolls to bottom anytime page loads or new convo is loaded
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  // for rendering chat message bubbles
  const renderMessages = () => {
    if (messages.length === 0) return (<span className="no-msgs-avail-span">No messages here</span>);
    return messages.map((message, i) => (
      <ChatMessage key={i} sender={message.sender} content={message.msg} />
    ))
  }


  return (
    <div className="chat-pane-div">
      <h2>AI Chat</h2>
      <div className="chat-msgs-div">
        {renderMessages()}
        <div ref={messagesEndRef} />
      </div>
        <Form onSubmit={handleSubmitMessage} className="chat-input-form">
          <InputGroup>
            <FormControl
              placeholder="Type a message..."
              value={input}
              onChange={(e) => setInput(e.target.value)}
            />
            <Button disabled={input.trim() === ""} className="send-btn" type="submit">
              Send
            </Button>
          </InputGroup>
      </Form>
    </div>
  )
};

export default ChatPane;