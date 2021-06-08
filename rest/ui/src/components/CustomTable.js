import {withStyles} from '@material-ui/core/styles';
import {useState} from 'react';
import {FormControl, InputLabel, Paper, Table, TableBody, TableCell, TableHead, TableRow, Select, MenuItem, Typography} from '@material-ui/core';

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

export default function CustomTable({title, header, data, create, initialOrderItem, hideOrder}) {
    const [orderItem, setOrderItem] = useState(initialOrderItem || 0);
    const [orderDirection, setDirection] = useState(0);

    return (
        <div style={{width: '100%'}}>
           <div className="list-header__container">
               <h5 className="card__title">{title}</h5>
               {create}
           </div>
           {!hideOrder && <div style={{paddingTop: "15px"}}>
               <FormControl>
                    <InputLabel id="demo-mutiple-name-label">Order by</InputLabel>
                    <Select value={orderItem}
                        label="Order by"
                        onChange={(e) => {
                            const index = e.target.value;
                            return setOrderItem(index);
                        }}>
                        {header.map((h, index) => <MenuItem value={index}>{h}</MenuItem>)}
                    </Select>
               </FormControl>
               <FormControl>
                    <InputLabel id="demo-mutiple-name-label">Direction</InputLabel>
                    <Select value={orderDirection}
                        label="Order by"
                        onChange={(e) => {
                            setDirection(e.target.value);
                        }}>
                        <MenuItem value={0}>Ascending</MenuItem>
                        <MenuItem value={1}>Descending</MenuItem>
                    </Select>
                </FormControl>
            </div>}
            <Table size="small" aria-label="a dense table" style={{marginTop: "15px"}}>
                <TableHead>
                    <TableRow>
                        {header.map((col, index) => <StyledTableCell key={`header_col_${index}`}><strong>{col}</strong></StyledTableCell>)}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data
                        .sort((d1, d2) => {
                            if (orderDirection === 0)
                                return d1[orderItem] > d2[orderItem]
                            else
                                return d1[orderItem] < d2[orderItem]
                        })
                        .map((row, index) => {
                            return (
                                <StyledTableRow key={`row_${index}`}>
                                    {row.map((cell, index) => <StyledTableCell
                                        key={`cell_${index}`}>{cell}</StyledTableCell>)}
                                </StyledTableRow>
                            );
                    })}
                </TableBody>
            </Table>
        </div>
    )
}
