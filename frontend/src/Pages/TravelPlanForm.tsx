import { useEffect, useState } from "react"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Link, useNavigate, useParams } from "react-router-dom"
import { createTravelPlan, getTravelPlan, updateTravelPlan } from "../components/TravelPlanFetch.tsx";
import type { TravelPlan } from "../types/TravelPlanTypes"

const timeRegex = /^([01]\d|2[0-3]):[0-5]\d$/ // "HH:mm"

const schema = z.object({
    type: z.string().min(3, "Min 3 characters").max(50, "Max 50 characters"),
    departureLocation: z.string().min(2).max(255),
    arrivalLocation: z.string().min(2).max(255),
    departureTime: z.string().regex(timeRegex, "Use HH:mm"),
    arrivalTime: z.string().regex(timeRegex, "Use HH:mm"),
    notes: z.union([z.string().length(0), z.string().max(1000)]).optional()
        .transform(v => (v && v.trim() === "" ? undefined : v)),
})

type TravelPlanFormData = z.infer<typeof schema>

const toHHmm = (hhmmss: string) => {
    const [h, m] = hhmmss.split(":")
    return `${h?.padStart(2,"0")}:${m?.padStart(2,"0")}`
}
const toHHmmss = (hhmm: string) => (hhmm.length === 5 ? `${hhmm}:00` : hhmm)

export default function TravelPlanForm() {
    const { adventureId, planId } = useParams()
    const isEdit = Boolean(planId)
    const navigate = useNavigate()
    const [loading, setLoading] = useState(isEdit)

    const { register, handleSubmit, reset, formState: { errors, isSubmitting } } =
        useForm<TravelPlanFormData>({ resolver: zodResolver(schema) as never })

    useEffect(() => {
        if (!isEdit || !planId) return
        getTravelPlan(planId).then((p: TravelPlan) => {
            reset({
                type: p.type,
                departureLocation: p.departureLocation,
                arrivalLocation: p.arrivalLocation,
                departureTime: toHHmm(p.departureTime),
                arrivalTime: toHHmm(p.arrivalTime),
                notes: p.notes ?? "",
            })
        }).finally(() => setLoading(false))
    }, [isEdit, planId, reset])

    async function onSubmit(values: TravelPlanFormData) {
        if (!adventureId) return
        const payload = {
            type: values.type,
            departureLocation: values.departureLocation,
            arrivalLocation: values.arrivalLocation,
            departureTime: toHHmmss(values.departureTime),
            arrivalTime: toHHmmss(values.arrivalTime),
            notes: values.notes,
            userAdventureId: Number(adventureId),
        }

        if (isEdit && planId) {
            await updateTravelPlan(planId, payload)
        } else {
            await createTravelPlan(adventureId, payload)
        }
        navigate(`/adventures/${adventureId}/travel`)
    }

    const input = "w-full rounded-xl border px-3 py-2 outline-none transition focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
    const err = "text-xs text-red-600"
    if (loading) return <div className="max-w-md mx-auto p-6">Loading…</div>

    return (
        <div className="max-w-md mx-auto p-6 space-y-4">
            <h1 className="text-2xl font-bold">{isEdit ? "Edit Travel Plan" : "Add Travel Plan"}</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <div>
                    <label className="text-sm text-gray-700">Type</label>
                    <input {...register("type")} className={`${input} mt-1 ${errors.type ? "border-red-500" : "border-gray-300"}`} />
                    {errors.type && <p className={err}>{errors.type.message}</p>}
                </div>

                <div>
                    <label className="text-sm text-gray-700">Departure location</label>
                    <input {...register("departureLocation")} className={`${input} mt-1 ${errors.departureLocation ? "border-red-500" : "border-gray-300"}`} />
                    {errors.departureLocation && <p className={err}>{errors.departureLocation.message}</p>}
                </div>

                <div>
                    <label className="text-sm text-gray-700">Arrival location</label>
                    <input {...register("arrivalLocation")} className={`${input} mt-1 ${errors.arrivalLocation ? "border-red-500" : "border-gray-300"}`} />
                    {errors.arrivalLocation && <p className={err}>{errors.arrivalLocation.message}</p>}
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <div>
                        <label className="text-sm text-gray-700">Departure time</label>
                        <input type="time" {...register("departureTime")}
                               className={`${input} mt-1 ${errors.departureTime ? "border-red-500" : "border-gray-300"}`} />
                        {errors.departureTime && <p className={err}>{errors.departureTime.message}</p>}
                    </div>
                    <div>
                        <label className="text-sm text-gray-700">Arrival time</label>
                        <input type="time" {...register("arrivalTime")}
                               className={`${input} mt-1 ${errors.arrivalTime ? "border-red-500" : "border-gray-300"}`} />
                        {errors.arrivalTime && <p className={err}>{errors.arrivalTime.message}</p>}
                    </div>
                </div>

                <div>
                    <label className="text-sm text-gray-700">Notes</label>
                    <textarea {...register("notes")} className={`${input} mt-1 border-gray-300`} />
                    {errors.notes && <p className={err}>{errors.notes.message}</p>}
                </div>

                <button
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full rounded-xl bg-blue-600 px-4 py-2 text-white font-medium shadow hover:opacity-90 transition disabled:opacity-60"
                >
                    {isEdit ? "Save changes" : "Add Travel Plan"}
                </button>
            </form>

            <Link to={`/adventures/${adventureId}/travel`} className="block text-center mt-4 text-blue-600 hover:underline">
                ← Back to travel list
            </Link>
        </div>
    )
}