import { useEffect, useState } from "react"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { useNavigate, useParams, Link } from "react-router-dom"
import { createAdventure, getAdventure, updateAdventure } from "../components/AdventuresFecth.tsx";
import type { CreateAdventurePayload, Adventure } from "../types/AdventureTypes.ts";

const schema = z.object({
    name: z.string().min(5).max(30),
    location: z.string().min(3).max(20),
    description: z.string().min(20).max(300),
    date: z.string().min(1),
    notes: z.string().max(500).optional(),
    lengthInDays: z.coerce.number().int().min(0),
    lengthInKm: z.coerce.number().min(0),
    startLocation: z.string().min(10).max(50),
    endLocation: z.string().min(10).max(50),
})

type AdventureFormData = z.infer<typeof schema>

export default function AdventureForm() {
    const navigate = useNavigate()
    const { id } = useParams()
    const isEdit = Boolean(id)
    const [loading, setLoading] = useState(isEdit)

    const { register, handleSubmit, reset, formState: { errors, isSubmitting } } =
        useForm<AdventureFormData>({
            resolver: zodResolver(schema) as never,
        })

    useEffect(() => {
        if (!isEdit || !id) return
        getAdventure(id)
            .then((a: Adventure) => {
                const d = new Date(a.date)
                const yyyy = d.getFullYear()
                const mm = String(d.getMonth() + 1).padStart(2, "0")
                const dd = String(d.getDate()).padStart(2, "0")

                reset({
                    name: a.name,
                    location: a.location ?? "",
                    description: a.description ?? "",
                    date: `${yyyy}-${mm}-${dd}`,
                    notes: a.notes ?? "",
                    lengthInDays: a.lengthInDays,
                    lengthInKm: a.lengthInKm,
                    startLocation: a.startLocation ?? "",
                    endLocation: a.endLocation ?? "",
                })
            })
            .finally(() => setLoading(false))
    }, [id, isEdit, reset])


    async function onSubmit(values: AdventureFormData) {
        const payload: CreateAdventurePayload = {
            name: values.name,
            location: values.location,
            description: values.description,
            date: values.date,
            notes: values.notes && values.notes.trim() !== "" ? values.notes : undefined,
            lengthInDays: values.lengthInDays,
            lengthInKm: values.lengthInKm,
            startLocation: values.startLocation,
            endLocation: values.endLocation,
        }

        if (isEdit && id) {
            await updateAdventure(id, payload)
        } else {
            await createAdventure(payload)
        }
        navigate("/dashboard")
    }


    const input = "w-full rounded-xl border px-3 py-2 outline-none transition focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
    const err = "text-xs text-red-600"

    if (loading) {
        return <div className="max-w-md mx-auto p-6">Loading…</div>
    }

    return (
        <div className="max-w-md mx-auto p-6 space-y-4">
            <h1 className="text-2xl font-bold">{isEdit ? "Edit Adventure" : "Add Adventure"}</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <div>
                    <label className="text-sm text-gray-700">Name</label>
                    <input {...register("name")} className={`${input} mt-1 ${errors.name ? "border-red-500" : "border-gray-300"}`} />
                    {errors.name && <p className={err}>{errors.name.message}</p>}
                </div>

                <div>
                    <label className="text-sm text-gray-700">Location</label>
                    <input {...register("location")} className={`${input} mt-1 ${errors.location ? "border-red-500" : "border-gray-300"}`} />
                    {errors.location && <p className={err}>{errors.location.message}</p>}
                </div>

                <div>
                    <label className="text-sm text-gray-700">Date</label>
                    <input type="date" {...register("date")} className={`${input} mt-1 ${errors.date ? "border-red-500" : "border-gray-300"}`} />
                    {errors.date && <p className={err}>{errors.date.message}</p>}
                </div>

                <div>
                    <label className="text-sm text-gray-700">Description</label>
                    <textarea {...register("description")} className={`${input} mt-1 ${errors.description ? "border-red-500" : "border-gray-300"}`} />
                    {errors.description && <p className={err}>{errors.description.message}</p>}
                </div>

                <div>
                    <label className="text-sm text-gray-700">Notes</label>
                    <textarea {...register("notes")} className={`${input} mt-1 border-gray-300`} />
                    {errors.notes && <p className={err}>{errors.notes.message}</p>}
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <div>
                        <label className="text-sm text-gray-700">Length (days)</label>
                        <input type="number" {...register("lengthInDays")} className={`${input} mt-1 ${errors.lengthInDays ? "border-red-500" : "border-gray-300"}`} />
                        {errors.lengthInDays && <p className={err}>{errors.lengthInDays.message}</p>}
                    </div>
                    <div>
                        <label className="text-sm text-gray-700">Distance (km)</label>
                        <input type="number" step="0.1" {...register("lengthInKm")} className={`${input} mt-1 ${errors.lengthInKm ? "border-red-500" : "border-gray-300"}`} />
                        {errors.lengthInKm && <p className={err}>{errors.lengthInKm.message}</p>}
                    </div>
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <div>
                        <label className="text-sm text-gray-700">Start Location</label>
                        <input {...register("startLocation")} className={`${input} mt-1 ${errors.startLocation ? "border-red-500" : "border-gray-300"}`} />
                        {errors.startLocation && <p className={err}>{errors.startLocation.message}</p>}
                    </div>
                    <div>
                        <label className="text-sm text-gray-700">End Location</label>
                        <input {...register("endLocation")} className={`${input} mt-1 ${errors.endLocation ? "border-red-500" : "border-gray-300"}`} />
                        {errors.endLocation && <p className={err}>{errors.endLocation.message}</p>}
                    </div>
                </div>

                <button
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full rounded-xl bg-blue-600 px-4 py-2 text-white font-medium shadow hover:opacity-90 transition disabled:opacity-60"
                >
                    {isEdit ? "Save changes" : "Add Adventure"}
                </button>
            </form>

            <Link to="/dashboard" className="block text-center mt-4 text-blue-600 hover:underline">← Back to list</Link>
        </div>
    )
}