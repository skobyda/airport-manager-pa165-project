import { withStyles } from '@material-ui/core/styles';
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
} from '@material-ui/core';

const StyledTableCell = withStyles((theme) => ({
    head: {
        backgroundColor: theme.palette.common.white,
        color: theme.palette.common.black,
    },
    body: {
        fontSize: 14,
    },
}))(TableCell);

const StyledTableRow = withStyles((theme) => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
        },
    },
}))(TableRow);

export default function CustomTable({title, header, data, create}) {
    return (
        <div style={{width: '100%'}}>
            <div className="list-header__container">
            <h5 className="card__title">{title}</h5>
                {create}
            </div>
            <Table size="small" aria-label="a dense table" style={{marginTop: "15px"}}>
                <TableHead>
                    <TableRow>
                        {header.map((col, index) => <StyledTableCell key={`header_col_${index}`}><strong>{col}</strong></StyledTableCell>)}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.map((row, index) => {
                        return (
                            <StyledTableRow key={`row_${index}`}>
                                {row.map((cell, index) => <StyledTableCell key={`cell_${index}`}>{cell}</StyledTableCell>)}
                            </StyledTableRow>
                        );
                    })}
                </TableBody>
            </Table>
        </div>
    )
}
