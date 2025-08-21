import { useParams, Link } from "react-router-dom"

export default function TrainingDetails() {
    const { id } = useParams()
    return (
        <div className="max-w-2xl mx-auto p-6">
            <h1 className="text-2xl font-bold mb-4">Training Details</h1>
            <p>Showing details for training <strong>{id}</strong></p>
            <Link to={`/training/${id}/edit`} className="text-blue-600 underline">Edit</Link>
            <br/>
            <Link to="/dashboard" className="block text-center mt-4 text-blue-600 hover:underline">
                ← Back to Dashboard
            </Link>
        </div>
    )
}
