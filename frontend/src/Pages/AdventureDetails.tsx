import { useParams, Link } from "react-router-dom"

export default function AdventureDetails() {
    const { id } = useParams()
    return (
        <div className="max-w-2xl mx-auto p-6">
            <h1 className="text-2xl font-bold mb-4">Adventure Details</h1>
            <p>Showing details for adventure <strong>{id}</strong></p>
            <Link to={`/adventures/${id}/edit`} className="text-blue-600 underline">Edit</Link>
            <br/>
            <Link to="/dashboard" className="block text-center mt-4 text-blue-600 hover:underline">
                ← Back to Dashboard
            </Link>
        </div>
    )
}
