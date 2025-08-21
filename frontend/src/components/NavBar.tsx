import { NavLink } from "react-router-dom"

export default function Navbar() {
    const linkClass = ({ isActive }: { isActive: boolean }) =>
        `text-lg font-medium transition duration-200 
   ${isActive
            ? "bg-gradient-to-r from-blue-500 to-purple-500 bg-clip-text text-transparent"
            : "text-gray-600 hover:bg-gradient-to-r hover:from-blue-500 hover:to-purple-500 hover:bg-clip-text hover:text-transparent"}`;

    return (
        <nav className="sticky top-0 z-50 bg-white shadow-md">
            <div className="max-w-7xl mx-auto px-6">
                <div className="flex justify-between h-16 items-center">
                    <NavLink to="/" className="flex items-center space-x-2">
                        <img src="/favicon.ico" alt="Adventure Planner Logo" className="h-10 w-auto" />
                        <span className="text-2xl font-bold bg-gradient-to-r from-blue-500 to-purple-500 bg-clip-text text-transparent">
                            Adventure Planner
                        </span>
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
    );
}
