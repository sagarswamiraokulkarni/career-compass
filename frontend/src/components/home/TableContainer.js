import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import Table from './Table';
import {AiOutlineEye, AiOutlineEdit, AiOutlineDelete, AiFillStar, AiOutlineStar, AiOutlineSearch} from 'react-icons/ai';
import Chips from 'react-chips';
import './TableContainer.css';
import ConfirmationModal from "./ConfirmationModal";

const TableContainer = () => {
    const navigate = useNavigate();
    const [tags, setTags] = useState([]);
    const [showSearchBar, setShowSearchBar] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [selectedRowData, setSelectedRowData] = useState(null);
    const dataJson = [
        {
            id: 1,
            star: true,
            companyName: 'Company A',
            role: 'Developer',
            appliedOn: '2024-03-01',
            status: 'Pending',
            field1: 'field1',
            field2: 'filed2',
            tags: ['tag1', 'tag2'],
            joburl: 'https://blog.logrocket.com/react-table-complete-guide/'
        },
        {
            id: 2,
            star: true,
            companyName: 'Company B',
            role: 'Tester',
            appliedOn: '2024-04-01',
            status: 'Pending',
            field1: 'field1',
            field2: 'filed2',
            tags: ['tag3', 'tag4'],
            joburl: 'https://www.npmjs.com/package/react-chips'
        },
    ];
    const [data, setData]=useState(dataJson);





    const columns = [
        {
            Header: 'Starred',
            accessor: 'star',
            Cell: ({ row }) => (
                <div>
                    {row.original.star ? <AiFillStar className="action-icon"/> : <AiOutlineStar className="action-icon"/>}
                </div>
            )
        },
        { Header: 'Company Name', accessor: 'companyName' },
        {
            Header: 'Job URL',
            accessor: 'joburl',
            Cell: ({ row }) => (
                <a href={row.original.joburl} target="_blank" rel="noopener noreferrer">
                    {row.original.companyName}
                </a>
            )
        },
        { Header: 'Role', accessor: 'role' },
        { Header: 'Applied On', accessor: 'appliedOn' },
        { Header: 'Status', accessor: 'status' },
        {
            Header: 'Tags',
            accessor: 'tags',
            Cell: ({ value }) => (
                <div className="tags-cell">
                    {value.map((tag, index) => (
                        <span key={index} className={`tag tag-${index % 4}`}>
              {tag}
            </span>
                    ))}
                </div>
            ),
        },
        {
            Header: 'Actions',
            accessor: 'actions',
            Cell: ({ row }) => (
                <>
                    <AiOutlineEye onClick={() => handleView(row.original)} className="action-icon" />
                    <AiOutlineEdit onClick={() => handleEdit(row.original)} className="action-icon" />
                    <AiOutlineDelete onClick={() => handleDelete(row.original)} className="action-icon" />
                </>
            )
        }
    ];

    const handleView = (rowData) => {
        console.log('View:', rowData);
        navigate('/details', { state: { rowData } });
    };

    const handleEdit = (rowData) => {
        console.log('Edit:', rowData);
        navigate('/edit', { state: { rowData } });
    };

    const handleDelete = (rowData) => {
        setSelectedRowData(rowData); // Store the selected row data
        setShowDeleteModal(true); // Open the delete modal
    };

    const confirmDelete = () => {
        console.log('Delete:', selectedRowData);
        const updatedData = data.filter(item => item.id !== selectedRowData.id);
        setData(updatedData);
        setShowDeleteModal(false);
    };


    const handleChange = (newTags) => {
        setTags(newTags);
    };

    const toggleSearchBar = () => {
        setShowSearchBar(!showSearchBar);
    };

    const filteredData = tags.length > 0
        ? data.filter((item) => tags.every((tag) => item.tags.includes(tag)))
        : data;

    return (
        <div className="search-table-container">
            <div className="search-container">
                <button className="search-button" onClick={toggleSearchBar}>
                    <AiOutlineSearch className="search-icon"/>
                    <span className="search-text">Search by Tags</span>
                </button>
                {showSearchBar && (
                    <div className="search-bar">
                        <Chips
                            value={tags}
                            onChange={handleChange}
                            suggestions={['tag1', 'tag2', 'tag3', 'tag4']}
                            placeholder="Type a tag and press enter..."
                            className="react-chips"
                        />
                    </div>
                )}
            </div>
            <div className="table-container">
            <Table data={filteredData} columns={columns} iconStyle={{fontSize: '24px', marginRight: '12px'}}/>
            </div>
            <ConfirmationModal show={showDeleteModal} onHide={() => setShowDeleteModal(false)} onConfirm={confirmDelete} rowData={selectedRowData} />
        </div>
    );
};

export default TableContainer;