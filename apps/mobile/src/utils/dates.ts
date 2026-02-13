import { format, formatDistanceToNow, isPast, differenceInDays } from 'date-fns'

export const formatDate = (dateString: string): string => {
  return format(new Date(dateString), 'dd MMM yyyy')
}

export const formatRelative = (dateString: string): string => {
  return formatDistanceToNow(new Date(dateString), { addSuffix: true })
}

export const isExpired = (expiryDate: string | null): boolean => {
  if (!expiryDate) {
    return false
  }
  return isPast(new Date(expiryDate))
}

export const daysUntilExpiry = (expiryDate: string | null): number | null => {
  if (!expiryDate) {
    return null
  }
  return differenceInDays(new Date(expiryDate), new Date())
}
