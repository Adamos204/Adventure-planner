import {Routes, Route} from "react-router-dom"
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
import Templates from "./Pages/Templates.tsx";
import GearForm from "./Pages/GearForm.tsx";
import GearList from "./Pages/GearList.tsx";
import TravelPlanList from "./Pages/TravelPlanList.tsx";
import TravelPlanForm from "./Pages/TravelPlanForm.tsx";

function App() {
    return (
        <>
            <Navbar/>
            <Routes>
                <Route path="/" element={<About/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>

                <Route element={<RequireAuth/>}>
                    <Route path="/dashboard" element={<Dashboard/>}/>
                    <Route path="/templates" element={<Templates/>}/>

                    <Route path="/trainings" element={<TrainingsList/>}/>
                    <Route path="/trainings/new" element={<TrainingForm/>}/>
                    <Route path="/trainings/:id" element={<TrainingDetails/>}/>
                    <Route path="/trainings/:id/edit" element={<TrainingForm/>}/>

                    <Route path="/adventures/user/:id" element={<AdventuresList/>}/>
                    <Route path="/adventures/new" element={<AdventureForm/>}/>
                    <Route path="/adventures/:id" element={<AdventureDetails/>}/>
                    <Route path="/adventures/:id/edit" element={<AdventureForm/>}/>

                    <Route path="/adventures/:adventureId/gear" element={<GearList/>}/>
                    <Route path="/adventures/:adventureId/gear/new" element={<GearForm/>}/>
                    <Route path="/adventures/:adventureId/gear/:gearId/edit" element={<GearForm/>}/>

                    <Route path="/adventures/:adventureId/travel" element={<TravelPlanList/>}/>
                    <Route path="/adventures/:adventureId/travel/new" element={<TravelPlanForm/>}/>
                    <Route path="/adventures/:adventureId/travel/:planId/edit" element={<TravelPlanForm/>}/>


                </Route>

                <Route path="*" element={<NotFound/>}/>
            </Routes>
        </>
    )
}

export default App