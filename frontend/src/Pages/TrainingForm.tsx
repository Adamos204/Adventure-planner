import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import {Link, useNavigate, useParams} from "react-router-dom"

const schema = z.object({
    date: z.string(),
    type: z.string().min(1, "Type is required"),
    durationInMin: z.number().min(1, "Duration required"),
    durationInKm: z.number().optional(),
    notes: z.string().optional(),
})

type TrainingFormData = z.infer<typeof schema>

export default function TrainingForm() {
    const navigate = useNavigate()
    const { id } = useParams()
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<TrainingFormData>({
        resolver: zodResolver(schema),
        defaultValues: id
            ? {
                date: "2025-08-20",
                type: "Gym",
                durationInMin: 60,
                durationInKm: 5,
                notes: "Leg day workout",
            }
            : {},
    })

    function onSubmit(data: TrainingFormData) {
        if (id) {
            console.log("EDIT training", id, data)
        } else {
            console.log("CREATE training", data)
        }
        navigate("/dashboard")
    }

    return (
        <div className="max-w-md mx-auto p-6">
            <h1 className="text-2xl font-bold mb-4">{id ? "Edit Training" : "Add Training"}</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <input type="date" {...register("date")} className="w-full border rounded p-2" />
                <input {...register("type")} placeholder="Type" className="w-full border rounded p-2" />
                {errors.type && <p className="text-red-500 text-sm">{errors.type.message}</p>}
                <input
                    type="number"
                    {...register("durationInMin", { valueAsNumber: true })}
                    placeholder="Duration (min)"
                    className="w-full border rounded p-2"
                />
                <input
                    type="number"
                    {...register("durationInKm", { valueAsNumber: true })}
                    placeholder="Distance (km)"
                    className="w-full border rounded p-2"
                />
                <textarea {...register("notes")} placeholder="Notes" className="w-full border rounded p-2" />
                <button type="submit" className="w-full bg-blue-600 text-white rounded p-2">
                    {id ? "Save changes" : "Add Training"}
                </button>
            </form>
            <Link to="/dashboard" className="block text-center mt-4 text-blue-600 hover:underline">
                ← Back to Dashboard
            </Link>
        </div>
    )
}