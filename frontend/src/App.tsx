import {Routes, Route} from "react-router-dom";
import Register from "./Pages/Register.tsx";
import About from "./Pages/About.tsx";
import Dashboard from "./Pages/Dashboard.tsx";
import Login from "./Pages/Login.tsx";
import NotFound from "./Pages/NotFound.tsx";
import Navbar from "./components/NavBar.tsx";

function App() {
    return (
        <>
            <Navbar/>
            <Routes>
                <Route path="/" element={<About/>} />
                <Route path="/dashboard" element={<Dashboard/>} />
                <Route path="/login" element={<Login/>} />
                <Route path="/register" element={<Register/>} />
                <Route path="*" element={<NotFound/>} />
            </Routes>
        </>
    );
}

export default App;