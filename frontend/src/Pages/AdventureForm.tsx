import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import {Link, useNavigate, useParams} from "react-router-dom"

const schema = z.object({
    name: z.string().min(1, "Name required"),
    location: z.string().optional(),
    description: z.string().optional(),
    date: z.string(),
    notes: z.string().optional(),
    lengthInDays: z.number().optional(),
    lengthInKm: z.number().optional(),
    startLocation: z.string().optional(),
    endLocation: z.string().optional(),
})

type AdventureFormData = z.infer<typeof schema>

export default function AdventureForm() {
    const navigate = useNavigate()
    const { id } = useParams()
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<AdventureFormData>({
        resolver: zodResolver(schema),
        defaultValues: id
            ? {
                name: "Mountain Hike Updated",
                location: "Highlands",
                description: "Updated description",
                date: "2025-08-26",
                notes: "Bring raincoat",
                lengthInDays: 4,
                lengthInKm: 28,
                startLocation: "Fort William",
                endLocation: "Ben Nevis Summit",
            }
            : {},
    })

    function onSubmit(data: AdventureFormData) {
        if (id) {
            console.log("EDIT adventure", id, data)
        } else {
            console.log("CREATE adventure", data)
        }
        navigate("/dashboard")
    }

    return (
        <div className="max-w-md mx-auto p-6">
            <h1 className="text-2xl font-bold mb-4">{id ? "Edit Adventure" : "Add Adventure"}</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <input {...register("name")} placeholder="Name" className="w-full border rounded p-2" />
                {errors.name && <p className="text-red-500 text-sm">{errors.name.message}</p>}
                <input {...register("location")} placeholder="Location" className="w-full border rounded p-2" />
                <textarea {...register("description")} placeholder="Description" className="w-full border rounded p-2" />
                <input type="date" {...register("date")} className="w-full border rounded p-2" />
                <textarea {...register("notes")} placeholder="Notes" className="w-full border rounded p-2" />
                <input
                    type="number"
                    {...register("lengthInDays", { valueAsNumber: true })}
                    placeholder="Length (days)"
                    className="w-full border rounded p-2"
                />
                <input
                    type="number"
                    {...register("lengthInKm", { valueAsNumber: true })}
                    placeholder="Length (km)"
                    className="w-full border rounded p-2"
                />
                <input {...register("startLocation")} placeholder="Start Location" className="w-full border rounded p-2" />
                <input {...register("endLocation")} placeholder="End Location" className="w-full border rounded p-2" />
                <button type="submit" className="w-full bg-blue-600 text-white rounded p-2">
                    {id ? "Save changes" : "Add Adventure"}
                </button>
            </form>
            <Link to="/dashboard" className="block text-center mt-4 text-blue-600 hover:underline">
                ← Back to Dashboard
            </Link>
        </div>
    )
}