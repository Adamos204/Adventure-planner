import { NavLink } from "react-router-dom"

export default function Navbar() {
    const linkClass = ({ isActive }: { isActive: boolean }) =>
        `px-3 py-2 text-sm font-medium transition-colors duration-200
     ${isActive ? "text-blue-600 border-b-2 border-blue-600" : "text-gray-700 hover:text-blue-600"}`

    return (
        <nav className="sticky top-0 z-50 bg-white shadow-md">
            <div className="max-w-7xl mx-auto px-6">
                <div className="flex justify-between h-16 items-center">
                    <NavLink to="/" className="text-2xl font-bold text-blue-600">
                        Adventure Planner
                    </NavLink>
                    <div className="flex space-x-6">
                        <NavLink to="/" className={linkClass}>About</NavLink>
                        <NavLink to="/dashboard" className={linkClass}>Dashboard</NavLink>
                        <NavLink to="/login" className={linkClass}>Login</NavLink>
                        <NavLink to="/register" className={linkClass}>Register</NavLink>
                    </div>
                </div>
            </div>
        </nav>
    )
}
