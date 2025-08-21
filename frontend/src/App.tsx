import { Routes, Route } from "react-router-dom"
import Register from "./Pages/Register"
import About from "./Pages/About"
import Dashboard from "./Pages/Dashboard"
import Login from "./Pages/Login"
import NotFound from "./Pages/NotFound"
import Navbar from "./components/NavBar"
import TrainingForm from "./Pages/TrainingForm"
import AdventureForm from "./Pages/AdventureForm"
import TrainingDetails from "./Pages/TrainingDetails"
import AdventureDetails from "./Pages/AdventureDetails"
import TrainingsList from "./Pages/TrainingsList.tsx";
import AdventuresList from "./Pages/AdventuresList.tsx";
import RequireAuth from "./components/RequireAuth.tsx";

function App() {
    return (
        <>
            <Navbar />
            <Routes>
                <Route path="/" element={<About />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />

                <Route element={<RequireAuth />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/training" element={<TrainingsList />} />
                    <Route path="/training/new" element={<TrainingForm />} />
                    <Route path="/training/:id" element={<TrainingDetails />} />
                    <Route path="/training/:id/edit" element={<TrainingForm />} />
                    <Route path="/adventures/user/:id" element={<AdventuresList />} />
                    <Route path="/adventures/new" element={<AdventureForm />} />
                    <Route path="/adventures/:id" element={<AdventureDetails />} />
                    <Route path="/adventures/:id/edit" element={<AdventureForm />} />
                </Route>

                <Route path="*" element={<NotFound />} />
            </Routes>
        </>
    )
}

export default App