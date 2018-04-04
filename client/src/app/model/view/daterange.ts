export class DateRange {
     beginDate: string;
     endDate: string;
    public static copyOf(dateRange: DateRange): DateRange {
        return Object.assign(new DateRange(), dateRange);
    }

}