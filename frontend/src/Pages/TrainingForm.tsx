import { useEffect, useState } from "react"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { useNavigate, useParams, Link } from "react-router-dom"
import { createTraining, getTraining, updateTraining } from "../components/TrainingsFetch.tsx"
import type { CreateTrainingPayload, Training } from "../types/TrainingTypes"

const schema = z.object({
    date: z.string().min(1, "Date is required"),
    type: z.string().min(3, "Min 3 characters").max(20, "Max 20 characters"),
    durationInMin: z.coerce.number().int().min(0, "Must be ≥ 0"),
    durationInKm:  z.coerce.number().int().min(0, "Must be ≥ 0"),
    notes:z
        .union([z.string().length(0), z.string().min(3).max(100)])
        .optional()
        .transform((v) => (v && v.trim() === "" ? undefined : v)),                                         // optional, if present 3–100
});

type TrainingFormData = z.infer<typeof schema>

export default function TrainingForm() {
    const navigate = useNavigate()
    const { id } = useParams()
    const isEdit = Boolean(id)
    const [loading, setLoading] = useState(isEdit)

    const { register, handleSubmit, reset, formState: { errors, isSubmitting } } =
        useForm<TrainingFormData>({
            resolver: zodResolver(schema) as never,
        });

    useEffect(() => {
        if (!isEdit || !id) return
        getTraining(id).then((t: Training) => {
            const d = new Date(t.date)
            const yyyy = d.getFullYear()
            const mm = String(d.getMonth() + 1).padStart(2, "0")
            const dd = String(d.getDate()).padStart(2, "0")
            reset({
                date: `${yyyy}-${mm}-${dd}`,
                type: t.type,
                durationInMin: t.durationInMin,
                durationInKm: t.durationInKm,
                notes: t.notes ?? "",
            })
        }).finally(() => setLoading(false))
    }, [id, isEdit, reset])

    async function onSubmit(values: TrainingFormData) {
        const payload: CreateTrainingPayload = {
            date: values.date,
            type: values.type,
            durationInMin: values.durationInMin,
            durationInKm: values.durationInKm,
            notes: values.notes,
        }
        if (isEdit && id) {
            await updateTraining(id, payload)
        } else {
            await createTraining(payload)
        }
        navigate("/dashboard")
    }

    const input = "w-full rounded-xl border px-3 py-2 outline-none transition focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
    const err = "text-xs text-red-600"
    if (loading) return <div className="max-w-md mx-auto p-6">Loading…</div>

    return (
        <div className="max-w-md mx-auto p-6 space-y-4">
            <h1 className="text-2xl font-bold">{isEdit ? "Edit Training" : "Add Training"}</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <div>
                    <label className="text-sm text-gray-700">Date</label>
                    <input type="date" {...register("date")}
                           className={`${input} mt-1 ${errors.date ? "border-red-500" : "border-gray-300"}`} />
                    {errors.date && <p className={err}>{errors.date.message}</p>}
                </div>

                <div>
                    <label className="text-sm text-gray-700">Type</label>
                    <input {...register("type")} placeholder="Gym / Run / Hike"
                           className={`${input} mt-1 ${errors.type ? "border-red-500" : "border-gray-300"}`} />
                    {errors.type && <p className={err}>{errors.type.message}</p>}
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <div>
                        <label className="text-sm text-gray-700">Duration (min)</label>
                        <input type="number" {...register("durationInMin")}
                               className={`${input} mt-1 ${errors.durationInMin ? "border-red-500" : "border-gray-300"}`} />
                        {errors.durationInMin && <p className={err}>{errors.durationInMin.message}</p>}
                    </div>
                    <div>
                        <label className="text-sm text-gray-700">Distance (km)</label>
                        <input type="number" {...register("durationInKm")}
                               className={`${input} mt-1 ${errors.durationInKm ? "border-red-500" : "border-gray-300"}`} />
                        {errors.durationInKm && <p className={err}>{errors.durationInKm.message}</p>}
                    </div>
                </div>

                <div>
                    <label className="text-sm text-gray-700">Notes</label>
                    <textarea {...register("notes")}
                              className={`${input} mt-1 border-gray-300`} />
                    {errors.notes && <p className={err}>{errors.notes.message}</p>}
                </div>

                <button
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full rounded-xl bg-blue-600 px-4 py-2 text-white font-medium shadow hover:opacity-90 transition disabled:opacity-60"
                >
                    {isEdit ? "Save changes" : "Add Training"}
                </button>
            </form>

            <Link to="/training" className="block text-center mt-4 text-blue-600 hover:underline">
                ← Back to list
            </Link>
        </div>
    )
}